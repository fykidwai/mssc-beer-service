package guru.springframework.msscbeerservice.web.mappers;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {

    public OffsetDateTime asOffSetDateTime(final Timestamp ts) {
        if (ts == null) {
            return null;
        }
        return OffsetDateTime.of(ts.toLocalDateTime().getYear(), ts.toLocalDateTime().getMonthValue(),
            ts.toLocalDateTime().getDayOfMonth(), ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(),
            ts.toLocalDateTime().getSecond(), ts.toLocalDateTime().getNano(), ZoneOffset.UTC);
    }

    public Timestamp asTimestamp(final OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }
}
