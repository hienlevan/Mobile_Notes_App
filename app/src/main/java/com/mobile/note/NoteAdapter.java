package com.mobile.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.note.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    List<Note> mListNote;

    public NoteAdapter(List<Note> listNotes) {
        this.mListNote = listNotes;
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
        private LinearLayout mnote;
        private ImageView menupopupbutton;

        public NoteViewHolder(@NonNull View view) {
            super(view);
            txtNoteTitle = view.findViewById(R.id.txtNoteTitle);
            txtNoteContent = view.findViewById(R.id.txtNoteContent);
            mnote =view.findViewById(R.id.note);
            menupopupbutton = view.findViewById(R.id.menupopbutton);
        }
    }
}
