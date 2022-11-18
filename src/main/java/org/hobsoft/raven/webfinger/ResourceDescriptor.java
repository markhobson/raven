package org.hobsoft.raven.webfinger;

import java.net.URI;

/**
 * JSON Resource Descriptor.
 *
 * @see <a href="https://www.rfc-editor.org/rfc/rfc7033#section-4.4">The JSON Resource Descriptor (JRD)</a>
 */
public record ResourceDescriptor(URI subject)
{
}
