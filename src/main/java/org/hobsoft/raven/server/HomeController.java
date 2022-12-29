package org.hobsoft.raven.server;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController
{
	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String get()
	{
		return """
			<!DOCTYPE html>
			
			<html>
				<body>
					<h1>Raven</h1>
				</body>
			</html>
		""";
	}
}
