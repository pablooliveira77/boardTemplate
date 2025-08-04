package board.dio.persistence.entity;

import java.time.OffsetDateTime;

public class BlockEntity {
    private Long id;
    private OffsetDateTime blockedAt;
    private String blockReason;
    private OffsetDateTime unblockedAt;
    private String unblockReason; 
}
