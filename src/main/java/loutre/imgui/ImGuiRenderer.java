package loutre.imgui;

import imgui.*;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import lombok.Getter;
import loutre.imgui.lwjgl2.ImGuiDisplay;
import loutre.imgui.lwjgl2.ImGuiLWJGL2;


public class ImGuiRenderer {
    private final static ImGuiDisplay imGuiDisplay = new ImGuiDisplay();
    private final static ImGuiLWJGL2 imGuiImplGl2 = new ImGuiLWJGL2();

    @Getter
    private static boolean isCreated = false;

    public static void create() {
        if (isCreated) {
            return;
        }
        ImGui.createContext();
        ImPlot.createContext();

        final ImGuiIO data = ImGui.getIO();
        data.setIniFilename("imguilib.ini");
        data.setFontGlobalScale(1F);



        // data.setConfigFlags(ImGuiConfigFlags.DockingEnable);
        // In case you want to enable Viewports on Windows, you have to do this instead of the above line:
        // data.setConfigFlags(ImGuiConfigFlags.DockingEnable | ImGuiConfigFlags.ViewportsEnable);


        imGuiDisplay.init();
        imGuiImplGl2.init();
        isCreated = true;
    }


    public static void handleKey() {
        imGuiDisplay.onKey();
    }

    public static void handleMouse() {
        imGuiDisplay.onMouse();
    }


    public static void draw(final Runnable runnable) {
        if (isCreated) {
            imGuiDisplay.newFrame();
            ImGui.newFrame();
            runnable.run();
            ImGui.render();

            imGuiImplGl2.newFrame();
            imGuiImplGl2.renderDrawData(ImGui.getDrawData());

            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                ImGui.updatePlatformWindows();
                ImGui.renderPlatformWindowsDefault();
            }
        }
    }

    public static short[] getGlyphRangesChineseFull() {
        char[] ranges = {
                0x0020, 0x00FF,
                0x2000, 0x206F,
                0x3000, 0x30FF,
                0x31F0, 0x31FF,
                0xFF00, 0xFFEF,
                0xFFFD, 0xFFFD,
                0x4e00, 0x9FAF,
                0
        };

        short[] convertedRanges = new short[ranges.length];
        for (int i = 0; i < ranges.length; i++) {
            convertedRanges[i] = (short) ranges[i];
        }

        return convertedRanges;
    }


}