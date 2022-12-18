package org.hobsoft.raven.server;

import java.net.URI;

public record PublicKey(URI id, URI owner, String publicKeyPem)
{
}
