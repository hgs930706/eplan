package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCFacConstraintCapa is a Querydsl query type for CFacConstraintCapa
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCFacConstraintCapa extends EntityPathBase<CFacConstraintCapa> {

    private static final long serialVersionUID = 557307158L;

    public static final QCFacConstraintCapa cFacConstraintCapa = new QCFacConstraintCapa("cFacConstraintCapa");

    public final StringPath fab = createString("fab");

    public final NumberPath<Integer> item_value = createNumber("item_value", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> lm_time = createDateTime("lm_time", java.time.LocalDateTime.class);

    public final StringPath lm_user = createString("lm_user");

    public final StringPath score_item = createString("score_item");

    public final StringPath shift = createString("shift");

    public final DatePath<java.time.LocalDate> shift_date = createDate("shift_date", java.time.LocalDate.class);

    public final StringPath site = createString("site");

    public QCFacConstraintCapa(String variable) {
        super(CFacConstraintCapa.class, forVariable(variable));
    }

    public QCFacConstraintCapa(Path<? extends CFacConstraintCapa> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCFacConstraintCapa(PathMetadata metadata) {
        super(CFacConstraintCapa.class, metadata);
    }

}

