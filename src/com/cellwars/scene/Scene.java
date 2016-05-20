package com.cellwars.scene;

import com.cellwars.actor.*;
import com.cellwars.actor.Package;
import com.cellwars.client.Scheduler;
import com.sun.javafx.geom.Vec2d;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tam�s on 2015-05-01.
 */
public class Scene {

    private Scheduler scheduler;
    private SceneScreen sceneScreen;
    private Player player;
    private List<Player> enemies;
    private List<Package> cookies;
    private List<Package> mines;

    public Scene(Scheduler scheduler, SceneScreen sceneScreen, Player player) {
        this.scheduler = scheduler;
        this.sceneScreen = sceneScreen;
        this.player = player;
        this.enemies = new ArrayList<>();
        this.cookies = new ArrayList<>();
        this.mines = new ArrayList<>();
    }

    public void initRules(Map map, float cellRadius, float packageRadius) {
        Rules.getRules().setMap(map);
        Rules.getRules().setCellRadius(cellRadius);
        Rules.getRules().setPackageRadius(packageRadius);

    }

    public void initPlayer(String name, double x, double y) {
        player.setName(name);
        player.initCell(new Cell(new Vec2d(x, y)));
        Platform.runLater(() -> sceneScreen.drawCell(player.getCell().getBody(), player.getColor(), player.getName()));
    }

    public void initEnemy(String name, Color color, double x, double y) {
        Player enemy = new Player(name, color);
        enemy.initCell(new Cell(new Vec2d(x, y)));
        enemies.add(enemy);
        sceneScreen.drawCell(enemy.getCell().getBody(), enemy.getColor(), name);
    }

    public void initCooky(int id, double x, double y) {
        Cooky cooky = new Cooky(id, new Vec2d(x, y));
        cookies.add(cooky);
        sceneScreen.drawCooky(cooky.getBody());
    }

    public void initMine(int id, double x, double y) {
        Mine mine = new Mine(id, new Vec2d(x, y));
        mines.add(mine);
        sceneScreen.drawMine(mine.getBody());
    }

    public void initMap() {
        Platform.runLater(() -> {
            sceneScreen.drawMap();
            sceneScreen.getMap().setOnMouseClicked(event -> {
                moveRequest(event.getX(), event.getY());
            });
        });
    }


    public void moveRequest(double x, double y) {
        scheduler.request("MOVE", player.getName(), Double.toString(x), Double.toString(y));
    }

    public void updatePlayers(String name, double x, double y, Color color, double radius, boolean isDead) {
        Platform.runLater(() -> {
            if (this.player.getName().equals(name)) {
                this.player.getCell().setRadius(radius);
                this.player.getCell().setPosition(new Vec2d(x, y));
                if (isDead)
                    sceneScreen.spawnEffect(player.getCell().getBody());
            }
            else {
                Player player = findPlayer(name);

                if (player == null) {
                    initEnemy(name, color, x, y);
                    return;
                }
                player.getCell().setRadius(radius);
                player.getCell().setPosition(new Vec2d(x, y));

                if (isDead)
                    sceneScreen.spawnEffect(player.getCell().getBody());
            }
        });
    }

    public void updateCookies(int id, double x, double y) {
        Platform.runLater(() -> {
            Cooky cooky = (Cooky) findPackage(id, cookies);
            if (cooky == null) {
                initCooky(id, x, y);
                return;
            }
            if (cooky.getPosition().x != x ||
                cooky.getPosition().y != y)
                cooky.setPosition(new Vec2d(x, y));
        });
    }

    public void updateMines(int id, double x, double y) {
        Platform.runLater(() -> {
            Mine mine = (Mine) findPackage(id, mines);
            if (mine == null) {
                initMine(id, x, y);
                return;
            }
            if (mine.getPosition().x != x ||
                    mine.getPosition().y != y)
                mine.setPosition(new Vec2d(x, y));
        });
    }

    public Player findPlayer(String name){
        Player player = null;
        for (Player p : enemies) {
            if (p.getName().equals(name)) {
                player = p;
                break;
            }
        }
        return player;
    }

    private Package findPackage(int id, List<Package> list) {
        Package _package = null;
        for (Package p : list) {
            if (p.getId() == id) {
                _package = p;
                break;
            }
        }
        return _package;
    }
}
