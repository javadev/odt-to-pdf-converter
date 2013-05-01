/*
 * $Id$
 *
 * Copyright 2013 Valentyn Kolesnikov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.doctopdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.velocity.app.Velocity;

/**
 * Converts ODT to PDF.
 *
 * @author Valentyn Kolesnikov
 * @version $Revision$ $Date$
 */
public class PdfGenerator {
    private static final String USAGE = "Usage: java -jar doctopdf.jar template.odt data.xml";
    private XMLTag xmlTag;

    private String get(String path) {
        return xmlTag.hasTag(path) ? xmlTag.getText(path) : "";
    }

    private void createDocument(List<String> templates, List<String> xmls) {
        if (!xmls.isEmpty()) {
            xmlTag = XMLDoc.from(MergeXml.merge(xmls), true);
        }
        for (String template : templates) {
            try {
                // 1) Load ODT file by filling Velocity template engine and cache
                // it to the registry
                InputStream in = new FileInputStream(new File(template));
                IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
                    in, TemplateEngineKind.Velocity);
    
                // 2) Create context Java model
                IContext context = report.createContext();
                context.put("xml", xmlTag);
                context.put("fio", get("surname") + " " + get("firstName") + " " + get("middleName"));
    
                // 3) Generate report by merging Java model with the ODT
                OutputStream out = new FileOutputStream(new File(
                    template.replaceFirst("\\.odt$", ".pdf")));
                // report.process(context, out);
                Options options = Options.getTo(ConverterTypeTo.PDF).via(
                    ConverterTypeVia.ODFDOM);
                report.convert(context, options, out);
    
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XDocReportException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
           Log.info(USAGE);
           return;
        }
        List<String> templates = new ArrayList<String>();
        List<String> xmls = new ArrayList<String>();
        for (String arg : args) {
            if (arg.endsWith(".odt")) {
                templates.add(arg);
            } else if (arg.endsWith(".xml")) {
                xmls.add(arg);
            }
        }
        if (templates.isEmpty()) {
           Log.info(USAGE);
           return;
        }
        new PdfGenerator().createDocument(templates, xmls);
    }

}
