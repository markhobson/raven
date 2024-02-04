package org.hobsoft.raven.server;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("notes")
public record Note(@Id Long id, Long userId, String content)
{
}
