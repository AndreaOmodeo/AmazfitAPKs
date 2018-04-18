package es.LBA97.PSourceInverted;

import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.Arrays;
import java.util.LinkedList;

import es.LBA97.PSourceInverted.data.DataType;
import es.LBA97.PSourceInverted.data.MultipleWatchDataListenerAdapter;
import es.LBA97.PSourceInverted.widget.AnalogClockWidget;
import es.LBA97.PSourceInverted.widget.ClockWidget;
import es.LBA97.PSourceInverted.widget.DigitalClockWidget;
import es.LBA97.PSourceInverted.widget.Widget;

/**
 * Abstract base class for watch faces
 */
public abstract class AbstractWatchFace extends com.huami.watch.watchface.AbstractWatchFace {


    final ClockWidget clock;
    final LinkedList<Widget> widgets = new LinkedList<>();


    private class DigitalEngine extends com.huami.watch.watchface.AbstractWatchFace.DigitalEngine {

        private final DigitalClockWidget widget;

        public DigitalEngine(DigitalClockWidget widget) {
            this.widget = widget;
        }

        @Override
        protected void onPrepareResources(Resources resources) {
            this.widget.init(AbstractWatchFace.this);
            for (Widget widget : AbstractWatchFace.this.widgets) {
                widget.init(AbstractWatchFace.this);
                for (DataType type : widget.getDataTypes()) {
                    registerWatchDataListener(new MultipleWatchDataListenerAdapter(widget, type));
                }
            }
        }

        @Override
        protected void onDrawDigital(Canvas canvas, float width, float height, float centerX, float centerY, int seconds, int minutes, int hours, int year, int month, int day, int week, int ampm) {
            widget.onDrawDigital(canvas, width, height, centerX, centerY, seconds, minutes, hours, year, month, day, week, ampm);
            for (Widget widget : AbstractWatchFace.this.widgets) {
                canvas.translate(widget.getX(), widget.getY());
                widget.draw(canvas, width, height, centerX, centerY);
                canvas.translate(-widget.getX(), -widget.getY());
            }
        }
    }

    private class AnalogEngine extends com.huami.watch.watchface.AbstractWatchFace.AnalogEngine {

        private final AnalogClockWidget widget;

        public AnalogEngine(AnalogClockWidget widget) {
            this.widget = widget;
        }

        @Override
        protected void onPrepareResources(Resources resources) {
            this.widget.init(AbstractWatchFace.this);
            for (Widget widget : AbstractWatchFace.this.widgets) {
                widget.init(AbstractWatchFace.this);
                for (DataType type : widget.getDataTypes()) {
                    registerWatchDataListener(new MultipleWatchDataListenerAdapter(widget, type));
                }
            }
        }

        @Override
        protected void onDrawAnalog(Canvas canvas, float width, float height, float centerX, float centerY, float secRot, float minRot, float hrRot) {
            widget.onDrawAnalog(canvas, width, height, centerX, centerY, secRot, minRot, hrRot);
            for (Widget widget : AbstractWatchFace.this.widgets) {
                canvas.translate(widget.getX(), widget.getY());
                widget.draw(canvas, width, height, centerX, centerY);
                canvas.translate(-widget.getX(), -widget.getY());
            }
        }
    }


    protected AbstractWatchFace(ClockWidget clock, Widget... widgets) {
        this.clock = clock;
        this.widgets.addAll(Arrays.asList(widgets));
    }

    public final Engine onCreateEngine() {
        notifyStatusBarPosition(22.0f);
        return AnalogClockWidget.class.isInstance(this.clock) ? new AnalogEngine((AnalogClockWidget) this.clock) : new DigitalEngine((DigitalClockWidget) this.clock);
    }
}
