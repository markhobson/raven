package org.hobsoft.raven.server;

import java.security.KeyPair;
import java.util.List;

public record User(String name, KeyPair keyPair, List<Note> notes)
{
}
