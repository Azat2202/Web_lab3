package models;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import validators.PointValidator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

@Named("pointHandler")
@SessionScoped
public class PointHandler implements Serializable {

    private Point point = new Point();
    private LinkedList<Point> points = new LinkedList<>();

    public LinkedList<Point> getPoints() {
        return points;
    }

    public void add(){
        long timer = System.currentTimeMillis();
        point.setTime(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        point.setStatus(PointValidator.isHit(point.getX(), point.getY(), point.getR()));
        point.setScriptTime(System.currentTimeMillis() - timer);

        points.add(point);
        point = new Point(point.getX(), point.getY(), point.getR());
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
