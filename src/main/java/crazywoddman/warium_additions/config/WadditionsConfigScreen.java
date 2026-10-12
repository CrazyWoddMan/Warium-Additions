package crazywoddman.warium_additions.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.network.ConfigSyncPacket;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.Commands;

public class WadditionsConfigScreen extends Screen {
    private static final int
    WIDGET_WIDTH = 160,
    WIDTH = WIDGET_WIDTH * 2 + 10,
    WIDGET_HEIGHT = 20;
    private final Screen parent;
    private final Map<ConfigValueHolder, Object> changes = new HashMap<>();
    private final Map<String, List<ConfigValueHolder>> sections = new LinkedHashMap<>();
    private OptionsList configList;
    private Status status;
    private int sectionsX;
    private Button save;
    private String section;

    private WadditionsConfigScreen(Minecraft mc, Screen parent) {
        super(Component.literal("Warium Additions Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.sections.clear();
        this.sectionsX = 0;

        if (this.minecraft.level == null) {
            this.status = Status.NOT_IN_WORLD;
        } else if (!this.minecraft.player.hasPermissions(Commands.LEVEL_ADMINS)) {
            this.status = Status.NO_PERMISSIONS;
        } else {
            this.status = Status.READY;
            for (ConfigValueHolder holder : WadditionsConfig.VALUES) {
                List<String> path = holder.getPath();
                String section = path.get(0);

                if (this.section == null)
                    this.section = section;

                List<ConfigValueHolder> holders = this.sections.get(section);

                if (holders == null)
                    sections.put(section, holders = new ArrayList<>());
                
                holders.add(holder);
            }
        }

        this.sections.forEach((section, values) -> this.sectionsX += this.font.width(section) + 11);
        this.sectionsX = (-this.sectionsX + 5) / 2;

        rebuild();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void rebuild() {
        clearWidgets();
        this.configList = new OptionsList(this.minecraft, this.width, this.height);

        if (this.status == Status.READY) {
            String lastSubCategory = "";

            for (ConfigValueHolder holder : this.sections.get(this.section)) {
                List<String> path = holder.getPath();
                String subCategory = path.size() > 2 ? path.get(path.size() - 2) : "";

                if (!subCategory.isEmpty() && !subCategory.equals(lastSubCategory)) {
                    lastSubCategory = subCategory;
                    this.configList.addEntry(makeText(Component.literal(subCategory), WIDTH).alignCenter());
                }

                Object value = holder.get();

                if (value instanceof Boolean) {
                    addToggle(holder);
                } else if (value instanceof Integer) {
                    addEditBox(holder, s -> Integer.valueOf(s));
                } else if (value instanceof Double) {
                    addEditBox(holder, s -> Double.valueOf(s));
                } else if (value instanceof String) {
                    addSelector(holder);
                } else {
                    throw new IllegalStateException("Unknown ConfigValue type: " + holder.getPath().stream().collect(Collectors.joining(".")));
                }
            }
        } else {
            this.configList.addEntry(makeText(
                Component.translatable("%s.config.%s".formatted(WariumAdditions.MODID, this.status.name().toLowerCase())),
                WIDTH
            ).alignCenter());
        }

        addRenderableWidget(this.configList);
        int x = this.sectionsX;

        for (String section : this.sections.keySet()) {
            int width = this.font.width(section) + 6;
            Button sectionButton = Button.builder(
                Component.literal(section),
                button -> {
                    this.section = section;
                    button.active = false;
                    rebuild();
                }
            ).bounds(this.width / 2 + x, 7, width, WIDGET_HEIGHT).build();
            x += width + 5;
            addRenderableWidget(sectionButton);

            if (this.section.equals(section))
                sectionButton.active = false;
        }

        this.save = addRenderableWidget(Button.builder(
            CommonComponents.GUI_DONE,
            button -> {
                if (!this.changes.isEmpty()) {
                    this.changes.forEach((holder, value) -> holder.set(value));
                    ConfigSyncPacket.send();
                }
                
                this.minecraft.setScreen(this.parent);
            }
        ).bounds(this.width / 2 + 5, this.height - 27, WIDGET_WIDTH, WIDGET_HEIGHT).build());

        if (this.status != Status.READY)
            save.active = false;

        addRenderableWidget(Button.builder(
            CommonComponents.GUI_CANCEL,
            button -> this.minecraft.setScreen(parent)
        ).bounds(this.width / 2 - WIDGET_WIDTH - 5, this.height - 27, WIDGET_WIDTH, WIDGET_HEIGHT).build());
    }

    private StringWidget makeText(Component text, int width) {
        return new StringWidget(
            0,
            0,
            width,
            WIDGET_HEIGHT,
            text,
            this.font
        );
    }

    @SuppressWarnings("unchecked")
    private void add(ConfigValueHolder value, BiFunction<Component, ResetButton, AbstractWidget> fun) {
        Component desc = Component.translatable(WariumAdditions.MODID + ".config." + value.getPath().stream().collect(Collectors.joining(".")));
        ResetButton reset = new ResetButton(value, parent -> {
            Object defaultValue = value.getDefault();

            if (parent instanceof EditBox editBox) {
                editBox.setValue(defaultValue.toString());
            } else if (parent instanceof CycleButton<?> cycleButton) {
                ((CycleButton<Object>)cycleButton).setValue(defaultValue);
                addChanged(value, defaultValue);
            } else {
                throw new IllegalStateException("Unknown AbstractWidget type");
            }
        });
        this.configList.addEntry(
            makeText(desc, WIDGET_WIDTH).alignLeft(),
            reset.parent = fun.apply(desc, reset),
            reset
        );
    }

    private void addToggle(ConfigValueHolder holder) {
        add(holder, (desc, reset) -> CycleButton
            .onOffBuilder((boolean)this.changes.getOrDefault(holder, holder.get()))
            .displayOnlyValue()
            .create(
                0,
                0,
                WIDGET_WIDTH - 60,
                WIDGET_HEIGHT,
                desc,
                (button, value) -> {
                    addChanged(holder, value);
                    reset.update(value);
                }
            )
        );
    }

    private <T> void addEditBox(ConfigValueHolder holder, Function<String, T> parser) {
        add(holder, (desc, reset) -> { 
            EditBox option = new EditBox(
                this.font,
                0,
                0,
                WIDGET_WIDTH - 60,
                WIDGET_HEIGHT,
                desc
            );
            option.setValue(this.changes.getOrDefault(holder, holder.get()).toString());
            option.setResponder(s -> {
                try {
                    T value = parser.apply(s);
                    
                    if (holder.test(value)) {
                        addChanged(holder, value);
                        option.setTextColor(14737632);
                        this.save.active = true;
                        reset.update(value);
                        return;
                    }
                } catch (NumberFormatException e) {}

                option.setTextColor(16733525);
                this.save.active = false;
                reset.active = true;
            });
            
            return option;
        });
    }

    private void addSelector(ConfigValueHolder holder) {
        if (holder.validator() instanceof SelectorValidator validator) {
            add(holder, (desc, reset) -> CycleButton
                .builder(Component::literal)
                .displayOnlyValue()
                .withValues(validator.values())
                .withInitialValue(this.changes.getOrDefault(holder, holder.get()).toString())
                .create(
                    0,
                    0,
                    WIDGET_WIDTH - 60,
                    WIDGET_HEIGHT,
                    desc,
                    (button, value) -> {
                        addChanged(holder, value);
                        reset.update(value);
                    }
                )
            );
        } else {
            throw new IllegalStateException("Invalid validator for " + holder.getPath().stream().collect(Collectors.joining(".")));
        }
    }

    private void addChanged(ConfigValueHolder holder, Object value) {
        if (holder.get().equals(value)) {
            this.changes.remove(holder);
        } else {
            this.changes.put(holder, value);
        }
    }

    @SuppressWarnings("removal")
    public static void register() {
        FMLJavaModLoadingContext.get().registerExtensionPoint(ConfigScreenFactory.class, () -> new ConfigScreenFactory(WadditionsConfigScreen::new));
    }

    private class ResetButton extends AbstractButton {
        private static final Component component = Component.translatable("controls.reset");
        private final ConfigValueHolder holder;
        private final Consumer<AbstractWidget> action;
        AbstractWidget parent;

        private ResetButton(ConfigValueHolder holder, Consumer<AbstractWidget> action) {
            super(0, 0, 50, WIDGET_HEIGHT, component);
            this.holder = holder;
            this.action = action;
            update(WadditionsConfigScreen.this.changes.getOrDefault(holder, holder.get()));
        }

        @Override
        public void onPress() {
            action.accept(this.parent);
            this.active = false;
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput output) {
            defaultButtonNarrationText(output);
        }

        void update(Object value) {
            this.active = !holder.getDefault().equals(value);
        }
    }

    private static class ConfigEntry extends ContainerObjectSelectionList.Entry<ConfigEntry> {
        private final List<AbstractWidget> widgets;

        private ConfigEntry(AbstractWidget... widgets) {
            this.widgets = List.of(widgets);
        }

        @Override
        public void render(
            GuiGraphics graphics,
            int index,
            int y,
            int x,
            int rowWidth,
            int rowHeight,
            int mouseX,
            int mouseY,
            boolean hovering,
            float partialTick
        ) {
            for (AbstractWidget widget : this.widgets) {
                widget.setX(x);
                widget.setY(y);
                x += widget.getWidth() + 10;
                widget.render(graphics, mouseX, mouseY, partialTick);
            }
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return this.widgets;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.widgets;
        }
    }

    private static class OptionsList extends ContainerObjectSelectionList<ConfigEntry> {

        OptionsList(Minecraft minecraft, int width, int height) {
            super (
                minecraft,
                width,
                height,
                40,
                height - 35,
                30
            );
        }

        @Override
        public int getRowWidth() {
            return WIDTH;
        }

        @Override
        protected int getScrollbarPosition() {
            return getRowRight() + 4;
        }

        public int addEntry(AbstractWidget... widgets) {
            return addEntry(new ConfigEntry(widgets));
        }
    }

    private enum Status {READY, NOT_IN_WORLD, NO_PERMISSIONS}
}

