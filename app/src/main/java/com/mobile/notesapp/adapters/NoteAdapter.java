package com.mobile.notesapp.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.notesapp.R;
import com.mobile.notesapp.entities.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public static final String TAG = "NoteAdapter";
    private ArrayList<Note> listNotes;
    private Context context;

    public NoteAdapter(ArrayList<Note> notes, Context context) {
        this.listNotes = notes;
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.note_item,parent,false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(itemView);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = listNotes.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getNoteText());

    }
    @Override
    public int getItemCount() {
        return listNotes.size();
    }
    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteTitle;
        private TextView noteContent;
        public NoteViewHolder(View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.txtNoteTitle);
            noteContent = (TextView) itemView.findViewById(R.id.txtNoteContent);
        }
    }
}
