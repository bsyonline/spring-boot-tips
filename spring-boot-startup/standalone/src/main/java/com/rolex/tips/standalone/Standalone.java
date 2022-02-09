package com.rolex.tips.standalone;

import com.rolex.tips.master.MasterServer;
import com.rolex.tips.worker.WorkerServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
public class Standalone {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MasterServer.class, WorkerServer.class).run(args);
    }
}
