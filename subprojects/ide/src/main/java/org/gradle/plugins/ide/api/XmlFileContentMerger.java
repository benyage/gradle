/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.plugins.ide.api;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.Incubating;
import org.gradle.api.XmlProvider;
import org.gradle.api.internal.provider.Providers;
import org.gradle.api.provider.Provider;
import org.gradle.internal.xml.XmlTransformer;

/**
 * Models the generation/parsing/merging capabilities.
 * Adds XML-related hooks.
 * <p>
 * For examples see docs for {@link org.gradle.plugins.ide.eclipse.model.EclipseProject}
 * or {@link org.gradle.plugins.ide.idea.model.IdeaProject} and others.
 */
public class XmlFileContentMerger extends FileContentMerger {

    private XmlTransformer xmlTransformer;
    private final Provider<XmlTransformer> xmlTransformerProvider;

    public XmlFileContentMerger(XmlTransformer xmlTransformer) {
        this(Providers.<XmlTransformer>notDefined());
        this.xmlTransformer = xmlTransformer;
    }

    public XmlFileContentMerger(Provider<XmlTransformer> xmlTransformerProvider) {
        this.xmlTransformerProvider = xmlTransformerProvider;
    }

    public XmlTransformer getXmlTransformer() {
        if (xmlTransformer != null) {
            return xmlTransformer;
        }
        return xmlTransformerProvider.getOrNull();
    }

    public void setXmlTransformer(XmlTransformer xmlTransformer) {
        this.xmlTransformer = xmlTransformer;
    }

    /**
     * Adds a closure to be called when the file has been created.
     * The XML is passed to the closure as a parameter in form of a {@link XmlProvider}.
     * The closure can modify the XML before it is written to the output file.
     * <p>
     * For examples see docs for {@link org.gradle.plugins.ide.eclipse.model.EclipseProject}
     * or {@link org.gradle.plugins.ide.idea.model.IdeaProject} and others.
     *
     * @param closure The closure to execute when the XML has been created.
     */
    public void withXml(Closure closure) {
        getXmlTransformer().addAction(closure);
    }

    /**
     * Adds an action to be called when the file has been created.
     * <p>
     * See {@link #withXml(Closure)}
     *
     * @param action The action to execute when the XML has been created.
     */
    @Incubating
    public void withXml(Action<? super XmlProvider> action) {
        getXmlTransformer().addAction(action);
    }
}
