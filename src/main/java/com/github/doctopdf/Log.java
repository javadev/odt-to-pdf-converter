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

import java.util.logging.*;

/**.
 * @author Valentyn Kolesnikov
 * @version $Revision$ $Date$
 */
public class Log  {
    private enum MessageType { DEBUG, INFO, WARN, ERROR };

    private static class LogFormatter extends Formatter {
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(1000);
            builder.append(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                new java.util.Date(record.getMillis()))).append(" ");
            builder.append(String.format("%-5s", record.getLevel())).append(" ");
            builder.append(record.getSourceClassName()).append(" - ");
            
            builder.append(formatMessage(record));
            builder.append("\n");
            return builder.toString();
        }
     
        public String getHead(Handler handler) {
            return super.getHead(handler);
        }
     
        public String getTail(Handler handler) {
            return super.getTail(handler);
        }
    }
    public static void debug(String param) {
        log(MessageType.DEBUG, param);
    }

    public static void error(String param) {
        log(MessageType.ERROR, param);
    }

    public static void error(Throwable aProblem, String param) {
        log(MessageType.ERROR, problem2String(param, aProblem));
     }

    public static void info(String param) {
        log(MessageType.INFO, param);
    }

    public static void warn(String param) {
        log(MessageType.WARN, param);
    }

    public static void warn(Throwable aProblem, String param) {
        warn(problem2String(param, aProblem));

    }

    private static void log(MessageType messageType, String localParam) {
        String aClassName = whoCalledMe();
        Logger logger = Logger.getLogger(aClassName);
        if (logger.getHandlers().length == 0) {
            logger.setUseParentHandlers(false);
            ConsoleHandler handler = new ConsoleHandler();
            handler.setFormatter(new LogFormatter());
            logger.addHandler(handler);
        }
        String param = modifyString(localParam);
        switch (messageType) {
        case DEBUG:
            logger.logp(Level.CONFIG, aClassName, "", param);
            break;
        case INFO:
            logger.logp(Level.INFO, aClassName, "", param);
            break;
        case WARN:
            logger.logp(Level.WARNING, aClassName, "", param);
            break;
        default:
            logger.logp(Level.SEVERE, aClassName, "",  param);
            break;
        }
    }

    private static String whoCalledMe() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTraceElements[4];
        String classname = caller.getClassName();
        return classname;
    }

    private static String problem2String(String aMsg, Throwable aProblem) {
        StringBuilder sb = new StringBuilder();
        if (aMsg != null) {
            sb.append(aMsg).append('\n');
        }
        sb.append("Error is: ").append(aProblem.getClass().getName()).
                append(" Message: ").append(aProblem.getMessage()).append('\n');
        makeGoodTrace(sb, aProblem.getStackTrace());
        Throwable cause = aProblem.getCause();
        while (cause != null) {
            sb.append("The cause is ").append(cause.getClass().getName()).
                    append(" Message: ").append(aProblem.getMessage()).append('\n');
            makeGoodTrace(sb, cause.getStackTrace());
            cause = cause.getCause();
        }
        return sb.toString();
    }

    private static String modifyString(String param) {
         if (null == param && "".equals(param)) {
             return "";
         }
         return param;
     }

    private static void makeGoodTrace(StringBuilder sb, StackTraceElement[] trace) {
        for (StackTraceElement entry : trace) {
            if (entry.getClassName().startsWith("com.github")) {
                sb.append("\t-->");
            } else {
                sb.append('\t');
            }
            sb.append(entry).append('\n');
        }
    }
}
