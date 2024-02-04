package org.hobsoft.raven.server;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
	@Id Long id,
	String name,
	@Column("public_key_pem") PublicKey publicKey,
	@Column("private_key_pem") PrivateKey privateKey
)
{
}
