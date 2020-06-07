package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSLotOpwp is a Querydsl query type for SLotOpwp
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSLotOpwp extends EntityPathBase<SLotOpwp> {

    private static final long serialVersionUID = -14492291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSLotOpwp sLotOpwp = new QSLotOpwp("sLotOpwp");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final QSLotOpwpId sLotOpwpId;

    public final NumberPath<Integer> wipQty = createNumber("wipQty", Integer.class);

    public QSLotOpwp(String variable) {
        this(SLotOpwp.class, forVariable(variable), INITS);
    }

    public QSLotOpwp(Path<? extends SLotOpwp> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSLotOpwp(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSLotOpwp(PathMetadata metadata, PathInits inits) {
        this(SLotOpwp.class, metadata, inits);
    }

    public QSLotOpwp(Class<? extends SLotOpwp> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sLotOpwpId = inits.isInitialized("sLotOpwpId") ? new QSLotOpwpId(forProperty("sLotOpwpId")) : null;
    }

}

