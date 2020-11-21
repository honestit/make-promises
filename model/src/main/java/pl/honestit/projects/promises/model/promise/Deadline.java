package pl.honestit.projects.promises.model.promise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deadline {

    private ZoneId zoneId;
    private LocalDate tillDate;
    private LocalTime tillTime;
    private LocalDate passDate;
    private LocalTime passTime;
    private Boolean kept;

    public Deadline(ZoneId zoneId, LocalDate tillDate, LocalTime tillTime) {
        this.zoneId = zoneId;
        this.tillDate = tillDate;
        this.tillTime = tillTime;
    }

    Deadline from(ZonedDateTime zonedDateTime) {
        return new Deadline(
                zonedDateTime.getZone(),
                zonedDateTime.toLocalDate(),
                zonedDateTime.toLocalTime()
        );
    }

    Deadline from(LocalDateTime localDateTime) {
        ZoneId zone = TimeZone.getDefault().toZoneId();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zone);
        return from(zonedDateTime);
    }
}
