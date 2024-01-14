package org.hobsoft.raven.server;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("USERS")
public record User(
	@Id Long id,
	String name,
	@Column("PUBLIC_KEY_PEM") PublicKey publicKey,
	@Column("PRIVATE_KEY_PEM") PrivateKey privateKey
)
{
}
