package org.jboss.mbui.client.aui.aim;

/**
 * @author Heiko Braun
 * @date 10/31/12
 */
public interface EventProducer<T extends Enum<T>> {

    boolean doesProduceEvents();

    void setProducedEvents(Event<T>... events);
}