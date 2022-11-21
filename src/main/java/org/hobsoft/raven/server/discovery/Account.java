package org.hobsoft.raven.server.discovery;

public record Account(String user, String host)
{
	public static Account parse(String account)
	{
		var at = account.indexOf('@');
		
		if (at == -1)
		{
			throw new IllegalArgumentException("Malformed account: " + account);
		}
		
		var user = account.substring(0, at);
		var host = account.substring(at + 1);
		
		return new Account(user, host);
	}
}
