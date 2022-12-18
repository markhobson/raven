package org.hobsoft.raven.server;

import java.security.KeyPair;

public record User(String name, KeyPair keyPair)
{
}
