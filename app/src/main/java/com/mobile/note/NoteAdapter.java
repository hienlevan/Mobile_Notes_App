package com.mobile.note;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.note.activity.EditNote;
import com.mobile.note.api.ApiService;
import com.mobile.note.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    List<Note> mListNote;

    public NoteAdapter(List<Note> listNotes) {
        this.mListNote = listNotes;
    }

    public void setFilteredList(List<Note> filteredList){
        this.mListNote = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = mListNote.get(position);
        if(note == null){
            return;
        }
        holder.txtNoteTitle.setText(note.getTitle());
        holder.txtNoteContent.setText(note.getContent());
        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(getRamdomColor(), null));

        ImageView popupbutton = holder.itemView.findViewById(R.id.menupopbutton);
        popupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Delete note").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                        ApiService.apiService.deteleNote(note.getNoteId()).enqueue(new Callback<Note>() {
                            @Override
                            public void onResponse(Call<Note> call, Response<Note> response) {
                                Toast.makeText(view.getContext(), "Deleted notes successfully", Toast.LENGTH_SHORT).show();
                                // Xóa note khỏi danh sách và cập nhật lại adapter
                                int position = mListNote.indexOf(note);
                                mListNote.remove(note);
                                notifyItemRemoved(position);
                            }

                            @Override
                            public void onFailure(Call<Note> call, Throwable t) {
                                Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return false;
                    }
                });

                popupMenu.getMenu().add("Edit note").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                        Intent intent = new Intent(view.getContext(), EditNote.class);
                        intent.putExtra("noteId", note.getNoteId());
                        intent.putExtra("noteTitle", note.getTitle());
                        intent.putExtra("noteContent", note.getContent());
                        ((Activity) view.getContext()).startActivityForResult(intent, 1);
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    private int getRamdomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.cat_bubble_1);
        colorCode.add(R.color.cat_bubble_2);
        colorCode.add(R.color.cat_bubble_3);
        colorCode.add(R.color.cat_bubble_4);
        colorCode.add(R.color.cat_bubble_5);
        colorCode.add(R.color.cat_bubble_6);
        Random random = new Random();
        int number = random.nextInt(colorCode.size());
        return colorCode.get(number);
    }

    @Override
    public int getItemCount() {
        if(mListNote!= null){
            return mListNote.size();
        }
        return 0;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNoteTitle;
        private TextView txtNoteContent;

        private CardView cardView;
        private LinearLayout mnote;
        private ImageView menupopupbutton;

        public NoteViewHolder(@NonNull View view) {
            super(view);
            txtNoteTitle = view.findViewById(R.id.txtNoteTitle);
            txtNoteContent = view.findViewById(R.id.txtNoteContent);
            mnote =view.findViewById(R.id.note);
            menupopupbutton = view.findViewById(R.id.menupopbutton);
            cardView = view.findViewById(R.id.noteCard);
        }
    }
}
