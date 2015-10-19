package org.loopf.sysdash.web;

import java.io.Writer;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerExceptionHandler implements TemplateExceptionHandler {
	public void handleTemplateException(TemplateException te, Environment env,
			Writer out) throws TemplateException {
		throw new TemplateException("freemarker error", te, env);
	}
}
