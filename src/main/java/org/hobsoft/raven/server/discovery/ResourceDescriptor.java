package org.hobsoft.raven.server.discovery;

import java.net.URI;
import java.util.List;

/**
 * JSON Resource Descriptor.
 *
 * @see <a href="https://www.rfc-editor.org/rfc/rfc7033#section-4.4">The JSON Resource Descriptor (JRD)</a>
 */
public record ResourceDescriptor(URI subject, List<Link> links)
{
}
