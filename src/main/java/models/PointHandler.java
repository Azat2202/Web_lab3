package models;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import validators.PointValidator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Map;

@Named("pointHandler")
@SessionScoped
public class PointHandler implements Serializable {

    private Point point = new Point();
    private LinkedList<Point> points = new LinkedList<>();

    public LinkedList<Point> getPoints() {
        return points;
    }

    public void add(){
        long timer = System.nanoTime();
        point.setTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        point.setStatus(PointValidator.isHit(point.getX(), point.getY(), point.getR()));
        point.setScriptTime((long) ((System.nanoTime() - timer) * 0.01));

        points.addFirst(point);
        point = new Point(point.getX(), point.getY(), point.getR());
    }

    public void addFromJS(){
        long timer = System.nanoTime();
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        params.values().forEach(System.out::println);

        try {
            float x = Float.parseFloat(params.get("x"));
            float y = Float.parseFloat(params.get("y"));
            float r = Float.parseFloat(params.get("r"));

            final Point attemptBean = new Point(x, y, r);
            attemptBean.setTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
            attemptBean.setStatus(PointValidator.isHit(x, y, r));
            attemptBean.setScriptTime((long) ((System.nanoTime() - timer) * 0.01));
            this.points.addFirst(attemptBean);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setPoints(LinkedList<Point> points) {
        this.points = points;
    }
}
