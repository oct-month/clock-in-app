package snnu.cs.clock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student
{
    private String schoolCode;
    private String name;
    private String className;
}
