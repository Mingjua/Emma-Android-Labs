package algonquin.cst2335.emmasandroidlabs;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {
    protected MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();

    public ChatRoomViewModel() {
        messages.setValue(new ArrayList<>());
    }

    public void addMessage(ChatMessage message) {
        ArrayList<ChatMessage> currentMessages = messages.getValue();
        currentMessages.add(message);
        messages.setValue(currentMessages);
    }

    public LiveData<ArrayList<ChatMessage>> getMessages() {
        return messages;
    }
}