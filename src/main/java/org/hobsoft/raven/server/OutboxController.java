package org.hobsoft.raven.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{username}/outbox")
public class OutboxController
{
}
