package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private double oldVolume;
    static Media url;

    static void checkIfURLNull() {
        if (url == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Alert");
            alert.setHeaderText("File not Opened");
            alert.setContentText("Please Open a File Before Running.");
            alert.showAndWait();
        }
    }

    MediaPlayer player;

    @FXML
    private Slider timeSilder;

    @FXML
    private Slider volumeSlider;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button playBtn;

    @FXML
    private Button backwardButton;

    @FXML
    private Button forwardButton;

    @FXML
    private Button muteBtn;

    @FXML
    private MenuItem slowestSpeed;


    //MENUBAR
    @FXML
    void openFileMenu(ActionEvent event) throws FileNotFoundException {
        //play button
        ImageView playImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/play.jpg"))).getImage());
        playImgView.setFitHeight(50);
        playImgView.setFitWidth(50);
        playImgView.setBlendMode(BlendMode.COLOR_BURN);
        try {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);

            Media media = new Media(file.toURI().toString());

            //to run new video even if one is already running
            if (player != null) {
                player.dispose();
            }
            url = media;

            player = new MediaPlayer(media);
            mediaView.setMediaPlayer(player);

            player.setOnReady(() -> {
                timeSilder.setMin(0);
//                timeSilder.setMax(player.getMedia().getDuration().toSeconds());
//                System.out.println((player.getMedia().getDuration().toSeconds()));
                timeSilder.setMax(player.getMedia().getDuration().toMinutes());
                timeSilder.setValue(0);
                playBtn.setGraphic(playImgView);
            });


            player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    Duration d = player.getCurrentTime();
                    timeSilder.setValue(d.toMinutes());
                }
            });

            timeSilder.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (timeSilder.isPressed()) {

                        double point = timeSilder.getValue();
                        player.seek(new Duration(point * 60000));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //BUTTONS
    @FXML
    void play(ActionEvent event) throws FileNotFoundException {
        //play button
        ImageView playImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/play.jpg"))).getImage());
        playImgView.setFitHeight(50);
        playImgView.setFitWidth(50);
        playImgView.setBlendMode(BlendMode.COLOR_BURN);

        //pause button
        ImageView pauseImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/pause.jpg"))).getImage());
        pauseImgView.setFitHeight(50);
        pauseImgView.setFitWidth(50);
        pauseImgView.setBlendMode(BlendMode.COLOR_BURN);


        checkIfURLNull();
        MediaPlayer.Status status = player.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            player.pause();
            playBtn.setGraphic(playImgView);

        } else {
            player.play();
            playBtn.setGraphic(pauseImgView);

        }
    }

    //MENU
    @FXML
    void playFromMenu(ActionEvent event) throws FileNotFoundException {
        checkIfURLNull();
        ImageView playImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/play.jpg"))).getImage());
        playImgView.setFitHeight(50);
        playImgView.setFitWidth(50);
        playImgView.setBlendMode(BlendMode.COLOR_BURN);

        //pause button
        ImageView pauseImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/pause.jpg"))).getImage());
        pauseImgView.setFitHeight(50);
        pauseImgView.setFitWidth(50);
        pauseImgView.setBlendMode(BlendMode.COLOR_BURN);

        MediaPlayer.Status status = player.getStatus();


        if (status == MediaPlayer.Status.PLAYING) {

        } else {
            player.play();
            playBtn.setGraphic(pauseImgView);

        }

    }

    @FXML
    void pauseFromMenu(ActionEvent event) throws FileNotFoundException {
        checkIfURLNull();

        ImageView playImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/play.jpg"))).getImage());
        playImgView.setFitHeight(50);
        playImgView.setFitWidth(50);
        playImgView.setBlendMode(BlendMode.COLOR_BURN);

        player.pause();
        playBtn.setGraphic(playImgView);
    }

    @FXML
    void backward(ActionEvent event) {
        checkIfURLNull();

        double currentDuration = player.getCurrentTime().toSeconds();
        currentDuration -= 10;
        player.seek(new Duration(currentDuration * 1000));
    }

    @FXML
    void backwardFromMenu(ActionEvent event) {
        checkIfURLNull();
        double currentDuration = player.getCurrentTime().toSeconds();
        currentDuration -= 10;
        player.seek(new Duration(currentDuration * 1000));
    }


    @FXML
    void forward(ActionEvent event) {
        checkIfURLNull();
        double currentDuration = player.getCurrentTime().toSeconds();
        currentDuration += 10;
        player.seek(new Duration(currentDuration * 1000));
    }

    @FXML
    void forwardFromMenu(ActionEvent event) {
        checkIfURLNull();
        double currentDuration = player.getCurrentTime().toSeconds();
        currentDuration += 10;
        player.seek(new Duration(currentDuration * 1000));
    }


    //SPEEDS
    //0.75
    @FXML
    void slowerSpeed(ActionEvent event) {
        checkIfURLNull();
        if (player != null) {
            player.setRate(0.75);
        }
    }

    //0.5X
    @FXML
    void slowerfineSpeed(ActionEvent event) {
        checkIfURLNull();
        if (player != null) {
            player.setRate(0.5);
        }
    }

    //0.25
    @FXML
    void slowestSpeed(ActionEvent event) {
        checkIfURLNull();
        if (player != null) {
            player.setRate(0.25);
        }
    }

    //1X
    @FXML
    void normalSpeed(ActionEvent event) {
        checkIfURLNull();
        if (player != null) {
            player.setRate(1);
        }
    }

    //1.25X
    @FXML
    void fastSpeed(ActionEvent event) {
        checkIfURLNull();
        if (player != null) {
            player.setRate(1.25);
        }
    }

    //1.5X
    @FXML
    void fasterSpeed(ActionEvent event) {
        checkIfURLNull();
        if (player != null) {
            player.setRate(1.5);
        }
    }

    //1.75
    @FXML
    void fasterfineSpeed(ActionEvent event) {
        checkIfURLNull();
        if (player != null) {
            player.setRate(1.75);
        }
    }

    //2
    @FXML
    void fastestSpeed(ActionEvent event) {
        checkIfURLNull();
        if (player != null) {
            player.setRate(2);
        }
    }

    @FXML
    void onMute(ActionEvent event) throws FileNotFoundException {
        checkIfURLNull();
        
        if (!player.isMute()) {
            oldVolume = player.getVolume();
            System.out.println("oldvolume : " + oldVolume);
        }
        //Sound Button
        ImageView soundImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/sound.jpg"))).getImage());
        soundImgView.setFitHeight(50);
        soundImgView.setFitWidth(50);
        soundImgView.setBlendMode(BlendMode.COLOR_BURN);
        muteBtn.setGraphic(soundImgView);

        //Mute Button
        ImageView muteImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/mute.jpg"))).getImage());
        muteImgView.setFitHeight(50);
        muteImgView.setFitWidth(50);
        muteImgView.setBlendMode(BlendMode.COLOR_BURN);

        if (!player.isMute()) {
            player.setMute(true);
            muteBtn.setGraphic(muteImgView);
            player.setVolume(0);
            volumeSlider.setValue(0);

        } else {
            player.setMute(false);
            muteBtn.setGraphic(soundImgView);
            player.setVolume(oldVolume);
            volumeSlider.setValue(oldVolume * 100);
            System.out.println("oldvolume set to: " + oldVolume);
            System.out.println("time slider set to: " + oldVolume * 100);
        }
    }

    @FXML
    void onVolumeDragged(MouseEvent event) {
        checkIfURLNull();
        double volume = (volumeSlider.getValue()) / 100; // Get the current slider value
        player.setVolume(volume); // Set the player's volume
        System.out.println("Volume adjusted during drag: " + volume);
    }

    @FXML
    void onVolumeClicked(MouseEvent event) {
        checkIfURLNull();
        double volume = (volumeSlider.getValue()) / 100; // Get the current slider value
        player.setVolume(volume); // Set the player's volume
        System.out.println("Volume adjusted during clicked: " + volume);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // play button
            ImageView playImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/play.jpg"))).getImage());
            playImgView.setFitHeight(50);
            playImgView.setFitWidth(50);
            playImgView.setBlendMode(BlendMode.COLOR_BURN);

            //pause button
            ImageView pauseImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/pause.jpg"))).getImage());
            pauseImgView.setFitHeight(50);
            pauseImgView.setFitWidth(50);
            pauseImgView.setBlendMode(BlendMode.COLOR_BURN);
            playBtn.setGraphic(playImgView);

            //forward button
            ImageView forwardImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/fastforward.png"))).getImage());
            forwardImgView.setFitHeight(50);
            forwardImgView.setFitWidth(50);
            forwardImgView.setBlendMode(BlendMode.COLOR_BURN);
            forwardButton.setGraphic(forwardImgView);

            //backward button
            ImageView backwardImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/slow.png"))).getImage());
            backwardImgView.setFitHeight(50);
            backwardImgView.setFitWidth(50);
            backwardImgView.setBlendMode(BlendMode.COLOR_BURN);
            backwardButton.setGraphic(backwardImgView);

            //Sound Button
            ImageView soundImgView = new ImageView(new ImageView(new Image(new FileInputStream("src/main/resources/images/sound.jpg"))).getImage());
            soundImgView.setFitHeight(50);
            soundImgView.setFitWidth(50);
            soundImgView.setBlendMode(BlendMode.COLOR_BURN);
            muteBtn.setGraphic(soundImgView);

            //VOLUME SLIDER
            volumeSlider.setValue(player.getVolume());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


