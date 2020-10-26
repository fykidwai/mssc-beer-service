package guru.springframework.msscbeerservice.web.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BeerPagedList extends PageImpl<BeerDto> {

    private static final long serialVersionUID = 1L;

    public BeerPagedList(final List<BeerDto> content) {
        super(content);
    }

    public BeerPagedList(final List<BeerDto> content, final Pageable pageable, final long total) {
        super(content, pageable, total);
    }

}
