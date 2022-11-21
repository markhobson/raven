package org.hobsoft.raven.server.discovery;

import java.net.URI;

/**
 * JSON Resource Descriptor link.
 *
 * @see <a href="https://www.rfc-editor.org/rfc/rfc7033#section-4.4.4">links</a>
 */
public record Link(String rel, String type, URI href)
{
}
