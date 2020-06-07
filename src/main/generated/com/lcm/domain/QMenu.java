package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = -651375132L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenu menu = new QMenu("menu");

    public final StringPath activeFlag = createString("activeFlag");

    public final ListPath<Menu, QMenu> childMenu = this.<Menu, QMenu>createList("childMenu", Menu.class, QMenu.class, PathInits.DIRECT2);

    public final NumberPath<Integer> displaySeq = createNumber("displaySeq", Integer.class);

    public final StringPath functionId = createString("functionId");

    public final StringPath functionName = createString("functionName");

    public final StringPath functionPath = createString("functionPath");

    public final StringPath language = createString("language");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final QMenu parentMenu;

    public final StringPath site = createString("site");

    public QMenu(String variable) {
        this(Menu.class, forVariable(variable), INITS);
    }

    public QMenu(Path<? extends Menu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenu(PathMetadata metadata, PathInits inits) {
        this(Menu.class, metadata, inits);
    }

    public QMenu(Class<? extends Menu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentMenu = inits.isInitialized("parentMenu") ? new QMenu(forProperty("parentMenu"), inits.get("parentMenu")) : null;
    }

}

