package com.gingos.ai.jutsugenerator.models.geminiai;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Content{
    private List<Part> parts;
}
