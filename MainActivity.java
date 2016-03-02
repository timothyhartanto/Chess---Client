package com.example.proto.chess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private Socket socket;

    private final int[] CHESS_PIECES_ID = {R.id.ivwhiteKing, R.id.ivwhiteQueen, R.id.ivwhiteKnight, R.id.ivwhiteBishop, R.id.ivwhiteRook,
            R.id.ivBlackKing, R.id.ivBlackQueen, R.id.ivBlackKnight, R.id.ivBlackBishop, R.id.ivBlackRook};

    private static final int SERVERPORT = 8181;
    private static final String SERVER_IP = "xx.xx.xx.xx"; //for testing with Chess--Server, use your router IP"xinuc.org";

    ImageView board;
    int height, width;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = (ImageView)findViewById(R.id.ivChessBoard);

        board.post(new Runnable() {
            @Override
            public void run() {
                // cannot get the size during onCreate and onResume, therefore the use of run()
                height = board.getMeasuredHeight() / 8;//height of imageView
                width = board.getMeasuredWidth() / 8;//width of imageView

                // set the size of all the pieces
                for (int i = 0; i < CHESS_PIECES_ID.length; i++) {
                    ImageView img = (ImageView) findViewById(CHESS_PIECES_ID[i]);
                    img.getLayoutParams().height = height;
                    img.getLayoutParams().width = width;
                }
            }
        });

        new Thread(new ClientThread()).start();

    }

    class ClientThread implements Runnable {
        private String action;
        private String[] actions;

        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);

                if (socket.isBound()) {
                    socket.setKeepAlive(true);

                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        while (true) {
                            action = in.readLine();
                            actions = action.split(" ");

                            //Log.d("Chess", in.readLine());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < actions.length; i++) {
                                        ImageView s = (ImageView) findViewById(checkPiece(actions[i].charAt(0)));
                                        s.setVisibility(View.VISIBLE);
                                        s.setX(horizontalPosition(Character.toLowerCase(actions[i].charAt(1))) * width);
                                        s.setY(verticalPosition(actions[i].charAt(2)) * height);
                                    }
                                }
                            });
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        private int checkPiece(char piece){
            switch(piece){
                case 'K':
                    return R.id.ivwhiteKing;
                case 'Q':
                    return R.id.ivwhiteQueen;
                case 'B':
                    return R.id.ivwhiteBishop;
                case 'N':
                    return R.id.ivwhiteKnight;
                case 'R':
                    return R.id.ivwhiteRook;
                case 'k':
                    return R.id.ivBlackKing;
                case 'q':
                    return R.id.ivBlackQueen;
                case 'b':
                    return R.id.ivBlackBishop;
                case 'n':
                    return R.id.ivBlackKnight;
                case 'r':
                    return R.id.ivBlackRook;
            }
            return 0;
        }

        private int horizontalPosition(char pos){
            switch(pos){
                case 'a':
                    return 0;
                case 'b':
                    return 1;
                case 'c':
                    return 2;
                case 'd':
                    return 3;
                case 'e':
                    return 4;
                case 'f':
                    return 5;
                case 'g':
                    return 6;
                case 'h':
                    return 7;
            }
            return 0;
        }

        private int verticalPosition(char pos){
            switch(pos){
                case '1':
                    return 7;
                case '2':
                    return 6;
                case '3':
                    return 5;
                case '4':
                    return 4;
                case '5':
                    return 3;
                case '6':
                    return 2;
                case '7':
                    return 1;
                case '8':
                    return 0;
            }
            return 0;
        }
    }
}
