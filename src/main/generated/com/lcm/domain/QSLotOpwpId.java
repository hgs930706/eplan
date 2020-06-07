package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSLotOpwpId is a Querydsl query type for SLotOpwpId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSLotOpwpId extends BeanPath<SLotOpwpId> {

    private static final long serialVersionUID = -1042187400L;

    public static final QSLotOpwpId sLotOpwpId = new QSLotOpwpId("sLotOpwpId");

    public final StringPath area = createString("area");

    public final StringPath fab = createString("fab");

    public final StringPath finalGrade = createString("finalGrade");

    public final StringPath lotType = createString("lotType");

    public final StringPath op = createString("op");

    public final StringPath partNo = createString("partNo");

    public final StringPath shift = createString("shift");

    public final DatePath<java.time.LocalDate> shiftDate = createDate("shiftDate", java.time.LocalDate.class);

    public final StringPath site = createString("site");

    public QSLotOpwpId(String variable) {
        super(SLotOpwpId.class, forVariable(variable));
    }

    public QSLotOpwpId(Path<? extends SLotOpwpId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSLotOpwpId(PathMetadata metadata) {
        super(SLotOpwpId.class, metadata);
    }

}

