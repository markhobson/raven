package org.hobsoft.raven.server;

import java.util.List;

import org.hobsoft.raven.server.KeyConverters.PemToPrivateKeyConverter;
import org.hobsoft.raven.server.KeyConverters.PemToPublicKeyConverter;
import org.hobsoft.raven.server.KeyConverters.PrivateKeyToPemConverter;
import org.hobsoft.raven.server.KeyConverters.PublicKeyToPemConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

@Configuration
public class ApplicationConfig extends AbstractJdbcConfiguration
{
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
