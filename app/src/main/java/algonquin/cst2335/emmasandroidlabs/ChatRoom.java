package algonquin.cst2335.emmasandroidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import algonquin.cst2335.emmasandroidlabs.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.emmasandroidlabs.databinding.ReceivedMessageBinding;
import algonquin.cst2335.emmasandroidlabs.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatRoomViewModel;
    RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatRoomViewModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize to the ViewModel arraylist:
//        messages = chatModel.getMessages().getValue();

        binding.sendButton.setOnClickListener( click -> {
            String inputText = binding.messageEditText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage sendMessage = new ChatMessage(inputText, currentDateAndTime, true);
            chatRoomViewModel.addMessage(sendMessage);
            //notify the adapter:
            myAdapter.notifyItemInserted(chatRoomViewModel.getMessages().getValue().size()-1); //redraw the missing row

            //clear the previous text
            binding.messageEditText.setText("");
        });

        binding.receivedButton.setOnClickListener( click -> {
            String inputText = binding.messageEditText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage receivedMessage = new ChatMessage(inputText, currentDateAndTime, false);
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

                if(viewType == 0){
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder( binding.getRoot());
                } else{
                    ReceivedMessageBinding binding = ReceivedMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder( binding.getRoot());
                }


            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage message = chatRoomViewModel.getMessages().getValue().get(position);
                holder.messageText.setText(message.getMessage());
                holder.timeText.setText(message.getTimeSent());
            }

            @Override
            public int getItemCount() {
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
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

    }
}