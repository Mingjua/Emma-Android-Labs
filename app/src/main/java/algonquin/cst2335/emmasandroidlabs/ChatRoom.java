package algonquin.cst2335.emmasandroidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.emmasandroidlabs.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.emmasandroidlabs.databinding.ReceivedMessageBinding;
import algonquin.cst2335.emmasandroidlabs.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatRoomViewModel;
    RecyclerView.Adapter myAdapter;
    RecyclerView recyclerView;
    ChatMessageDAO myDAO;

    protected ArrayList<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // calling onCreate from parent class
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        //loads an XML file on the page
        setContentView(binding.getRoot());

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        myDAO = db.cmDAO(); //the only function in MessageDatabase;

        chatRoomViewModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        //initialize to the ViewModel arraylist
        chatMessages = chatRoomViewModel.messages.getValue();

        Executor thread = Executors.newSingleThreadExecutor();
        //add all previous messages from database:
        thread.execute(() -> {
            List<ChatMessage> allMessages = myDAO.getAllMessages();
            chatMessages.addAll(allMessages);
        });

        binding.sendButton.setOnClickListener( click -> {
            String inputText = binding.messageEditText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage sendMessage = new ChatMessage(inputText, currentDateAndTime, true);

            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(() ->{
                myDAO.insertMessage(sendMessage);//add to database;
                /*this runs in another thread*/
            });

            //insert into ArrayList
            chatRoomViewModel.addMessage(sendMessage);

            //notify the adapter:
            myAdapter.notifyDataSetChanged(); //updates the rows

            //clear the previous text
            binding.messageEditText.setText("");
        });

        binding.receivedButton.setOnClickListener( click -> {
            String inputText = binding.messageEditText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage receivedMessage = new ChatMessage(inputText, currentDateAndTime, false);

            Executor thread2 = Executors.newSingleThreadExecutor();
            thread2.execute(() ->{
                myDAO.insertMessage(receivedMessage);//add to database;
                /*this runs in another thread*/
            });

            chatRoomViewModel.addMessage(receivedMessage);

            //notify the adapter:
            myAdapter.notifyItemInserted(chatRoomViewModel.getMessages().getValue().size()-1); //redraw the missing row

            //clear the previous text
            binding.messageEditText.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder >() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //this inflates the row layout

                //int viewType is what layout to load
                if(viewType == 0){
                                                                                                 //how big is parent?
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder( binding.getRoot());
                } else{
                    ReceivedMessageBinding binding = ReceivedMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder( binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                //update the widgets:
                ChatMessage message = chatRoomViewModel.getMessages().getValue().get(position);
                holder.messageText.setText(message.getMessage());
                holder.timeText.setText(message.getTimeSent()); //puts the string in position at theWord TextView
            }

            @Override
            public int getItemCount() {
                //how many rows there are:
                return chatRoomViewModel.getMessages().getValue().size();
            }
            @Override
            public int getItemViewType(int position)
            {
                ChatMessage message = chatRoomViewModel.getMessages().getValue().get(position);
                return message.getIsSentButton() ? 0 : 1;
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        // put variables for what is on a single row:
        TextView messageText;
        TextView timeText;

                            //This is a row:
        public MyRowHolder(@NonNull View itemView) {
            // In the MyRowViews constructor, you are passed the View parameter,
            // which in our lab is the ConstraintLayout root,
            // with an ImageView and two TextView subitems
            super(itemView);

            // set an onItemClicked() listener for when you click anywher on the area of the ConstraintLayout area
            // When you click on a row, we'd like to load an alert window asking if you want to delete this row
            itemView.setOnClickListener( click -> {

                // use the function getAbsoluteAdapterPosition(),
                // which will tell you which row (position) this row is currently in the adapter object
                int position = getAbsoluteAdapterPosition();

                // alert dialog to ask if you want to do this first
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                // set the message on the alert window
                builder.setMessage("Do you want to delete this?")
                        // set the title of the alert dialog
                        .setTitle("Question")
                        // AlertDialog gives you two buttons to use,
                        // a positive button, and a negative button.
                        // You can set the words of these buttons with the function

                                        // The "Yes" and "No" are the words that will show up on the buttons,
                                        // and the lambda function is the click handler for what happens if you click on each of those buttons
                        .setNegativeButton("No", (dl, clk)->{ /*Hide the dialog, do nothing*/})
                                        // For the "Yes" button, we have to remove the message that row,
                                        // delete it from the database, and update the Adapter object that something's been removed
                                        // so the RecyclerView can update itself:
                        .setPositiveButton("Yes",(dlg, clk)->{
                            ChatMessage toDelete = chatMessages.get(position);

                            Executor thread1 = Executors.newSingleThreadExecutor();
                            thread1.execute(() ->{
                                myDAO.deleteMessage( toDelete );
                                chatMessages.remove(position);//remove from our array list
                                // myAdapter.notifyItemRemoved(position);

                                // must be done on the main UI thread
                                runOnUiThread(() -> {  myAdapter.notifyDataSetChanged(); });
                            });

                                // A Snackbar is similar to a Toast, in that it can also show a message for a
                                // LENGTH_SHORT or LENGTH_LONG amount of time
                                Snackbar.make( messageText, "You deleted the message: " + position, Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", cl -> {
                                            Executor myThread = Executors.newSingleThreadExecutor();
                                            myThread.execute(() -> {
                                                myDAO.deleteMessage(toDelete);
                                                chatMessages.add(position,toDelete );
                                                runOnUiThread( () ->  myAdapter.notifyDataSetChanged());
                                            });
                                        })
                                        // make the window appear using the functions builder.create().show() this is called the builder pattern,
                                        // where the create() function returns an AlertDialog object,
                                        // which then has the .show() function called on the returned object.
                                        .show();
                                /*this runs in another thread*/
                        } )
                        .create().show();
            });

            //This holds the message Text:
            messageText = itemView.findViewById(R.id.message);
            //This holds the time text
            timeText = itemView.findViewById(R.id.time);
        }

    }
}