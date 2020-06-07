package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLine is a Querydsl query type for Line
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLine extends EntityPathBase<Line> {

    private static final long serialVersionUID = -651401095L;

    public static final QLine line1 = new QLine("line1");

    public final StringPath activeFlag = createString("activeFlag");

    public final StringPath aliasName = createString("aliasName");

    public final StringPath area = createString("area");

    public final StringPath fab = createString("fab");

    public final StringPath line = createString("line");

    public final StringPath lineMode = createString("lineMode");

    public final StringPath lineType = createString("lineType");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath site = createString("site");

    public QLine(String variable) {
        super(Line.class, forVariable(variable));
    }

    public QLine(Path<? extends Line> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLine(PathMetadata metadata) {
        super(Line.class, metadata);
    }

}

