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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import com.github.doctopdf.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

/**
 * Converts ODT to PDF.
 *
 * @author Valentyn Kolesnikov
 * @version $Revision$ $Date$
 */
public class PdfGenerator {

    public static void main(String[] args) {
        try {
            // 1) Load ODT file by filling Velocity template engine and cache
            // it to the registry
            InputStream in = PdfGenerator.class
                .getResourceAsStream("ODTProjectWithVelocity.odt");
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
                in, TemplateEngineKind.Velocity);

            // 2) Create context Java model
            IContext context = report.createContext();
            Project project = new Project("XDocReport");
            context.put("project", project);

            // 3) Generate report by merging Java model with the ODT
            OutputStream out = new FileOutputStream(new File(
                "ODTProjectWithVelocity_Out.pdf"));
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
