package com.ifernandez.proyectointegrador;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class ScheduleItem {

    @Id
    private Long id;
    private String time;
    private String text;
    private String day;
    private int pos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
