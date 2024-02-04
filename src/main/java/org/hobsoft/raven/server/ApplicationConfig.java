package org.hobsoft.raven.server;

import java.util.List;
import java.util.Optional;

import org.hobsoft.raven.server.KeyConverters.PemToPrivateKeyConverter;
import org.hobsoft.raven.server.KeyConverters.PemToPublicKeyConverter;
import org.hobsoft.raven.server.KeyConverters.PrivateKeyToPemConverter;
import org.hobsoft.raven.server.KeyConverters.PublicKeyToPemConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.RelationalManagedTypes;
import org.springframework.data.relational.core.mapping.NamingStrategy;

@Configuration
public class ApplicationConfig extends AbstractJdbcConfiguration
{
	@Override
	public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy,
		JdbcCustomConversions customConversions, RelationalManagedTypes jdbcManagedTypes)
	{
		var jdbcMappingContext = super.jdbcMappingContext(namingStrategy, customConversions, jdbcManagedTypes);
		
		// PostgreSQL defaults unquoted identifiers to lowercase, whereas HyperSQL uses uppercase. Disable quoted
		// identifiers in queries to allow both to coexist.
		jdbcMappingContext.setForceQuote(false);
		
		return jdbcMappingContext;
	}
	
	@Override
	protected List<?> userConverters()
	{
		return List.of(
			new PublicKeyToPemConverter(),
			new PemToPublicKeyConverter(),
			new PrivateKeyToPemConverter(),
			new PemToPrivateKeyConverter()
		);
	}
}
