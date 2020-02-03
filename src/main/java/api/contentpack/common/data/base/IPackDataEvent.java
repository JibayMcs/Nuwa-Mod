package api.contentpack.common.data.base;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import java.util.function.Consumer;

public interface IPackDataEvent {

    /**
     * This is the first of four commonly called events during mod initialization.
     * <p>
     * Called before {@link FMLClientSetupEvent} or {@link FMLDedicatedServerSetupEvent} during mod startup.
     * <p>
     * Called after {@link net.minecraftforge.event.RegistryEvent.Register} events have been fired.
     * <p>
     * Either register your listener using {@link net.minecraftforge.fml.AutomaticEventSubscriber} and
     * {@link net.minecraftforge.eventbus.api.SubscribeEvent} or
     * {@link net.minecraftforge.eventbus.api.IEventBus#addListener(Consumer)} in your constructor.
     * <p>
     * Most non-specific mod setup will be performed here. Note that this is a parallel dispatched event - you cannot
     * interact with game state in this event.
     *
     * @see net.minecraftforge.fml.DeferredWorkQueue to enqueue work to run on the main game thread after this event has
     * completed dispatch
     */
    void onCommonSetup(FMLCommonSetupEvent event);

    /**
     * This is the second of four commonly called events during mod lifecycle startup.
     * <p>
     * Called before {@link InterModEnqueueEvent}
     * Called after {@link FMLCommonSetupEvent}
     * <p>
     * Called on {@link net.minecraftforge.api.distmarker.Dist#CLIENT} - the game client.
     * <p>
     * Alternative to {@link FMLDedicatedServerSetupEvent}.
     * <p>
     * Do client only setup with this event, such as KeyBindings.
     * <p>
     * This is a parallel dispatch event.
     */
    void onClientSetup(FMLClientSetupEvent event);
}
