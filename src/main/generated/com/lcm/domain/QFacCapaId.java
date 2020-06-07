package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFacCapaId is a Querydsl query type for FacCapaId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QFacCapaId extends BeanPath<FacCapaId> {

    private static final long serialVersionUID = 365752333L;

    public static final QFacCapaId facCapaId = new QFacCapaId("facCapaId");

    public final StringPath area = createString("area");

    public final StringPath fab = createString("fab");

    public final StringPath scoreItem = createString("scoreItem");

    public final StringPath shift = createString("shift");

    public final DatePath<java.time.LocalDate> shiftDate = createDate("shiftDate", java.time.LocalDate.class);

    public final StringPath site = createString("site");

    public QFacCapaId(String variable) {
        super(FacCapaId.class, forVariable(variable));
    }

    public QFacCapaId(Path<? extends FacCapaId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFacCapaId(PathMetadata metadata) {
        super(FacCapaId.class, metadata);
    }

}

