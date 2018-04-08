package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.FoodAdapter;
import michaelbumes.therapysupportapp.adapter.MoodAdapter;
import michaelbumes.therapysupportapp.adapter.NoteAdapter;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends BaseFragment {
    CalendarView cv;

    private Calendar calStartOfDay, calEndOfDay;
    private RecyclerView recyclerViewMood, recyclerViewNote, recyclerViewFood;
    private RecyclerView.Adapter adapterMood, adapterNote, adapterFood;
    private List<MoodDiary> moodDiaries;
    private View mView;
    private Bundle mBundle;
    private SimpleDateFormat sdf;
    private TextView textViewMood, textViewNote, textViewFood;
    private Long selectedDateLong;


    public static CalendarFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_calendar);
        mView = view;
        mBundle = savedInstanceState;
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        cv = view.findViewById(R.id.calendar_view);
        selectedDateLong = cv.getDate();
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                Date date = null;
                try {
                    date = sdf.parse(String.valueOf(year)+"-"+String.valueOf(month+1)+ "-"+String.valueOf(dayOfMonth));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                calStartOfDay = Calendar.getInstance(TimeZone.getDefault());
                calStartOfDay.setTime(date); // compute start of the day for the timestamp
                calStartOfDay.set(Calendar.HOUR_OF_DAY, 0);
                calStartOfDay.set(Calendar.MINUTE, 0);
                calStartOfDay.set(Calendar.SECOND, 0);
                calStartOfDay.set(Calendar.MILLISECOND, 0);

                calEndOfDay = Calendar.getInstance(TimeZone.getDefault());
                calEndOfDay.setTime(date); // compute start of the day for the timestamp
                calEndOfDay.set(Calendar.HOUR_OF_DAY, 23);
                calEndOfDay.set(Calendar.MINUTE, 59);
                calEndOfDay.set(Calendar.SECOND, 59);
                calEndOfDay.set(Calendar.MILLISECOND, 999);
                updateRecyclerViews();


            }
        });
        Date date = new Date(selectedDateLong);

        calStartOfDay = Calendar.getInstance(TimeZone.getDefault());
        calStartOfDay.setTime(date); // compute start of the day for the timestamp
        calStartOfDay.set(Calendar.HOUR_OF_DAY, 0);
        calStartOfDay.set(Calendar.MINUTE, 0);
        calStartOfDay.set(Calendar.SECOND, 0);
        calStartOfDay.set(Calendar.MILLISECOND, 0);

        calEndOfDay = Calendar.getInstance(TimeZone.getDefault());
        calEndOfDay.setTime(date); // compute start of the day for the timestamp
        calEndOfDay.set(Calendar.HOUR_OF_DAY, 23);
        calEndOfDay.set(Calendar.MINUTE, 59);
        calEndOfDay.set(Calendar.SECOND, 59);
        calEndOfDay.set(Calendar.MILLISECOND, 999);
        textViewMood = view.findViewById(R.id.text_view_mood_calendar);
        textViewNote = view.findViewById(R.id.text_view_note_calendar);
        textViewFood = view.findViewById(R.id.text_view_food_calendar);

        updateRecyclerViews();







    }

    private void updateRecyclerViews() {

        moodDiaries = AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getAll();

        recyclerViewMood = mView.findViewById(R.id.mood_recyler_view);

        recyclerViewMood.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMood = new MoodAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewMood.setAdapter(adapterMood);

        recyclerViewNote = mView.findViewById(R.id.note_recyler_view);

        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterNote = new NoteAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewNote.setAdapter(adapterNote);

        recyclerViewFood = mView.findViewById(R.id.food_recyler_view);

        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterFood = new FoodAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewFood.setAdapter(adapterFood);

        if (adapterMood.getItemCount() != 0) {
            textViewMood.setVisibility(View.VISIBLE);
        } else {
            textViewMood.setVisibility(View.GONE);

        }
        if (adapterFood.getItemCount() != 0) {
            textViewFood.setVisibility(View.VISIBLE);
        } else {
            textViewFood.setVisibility(View.GONE);

        }
        if (adapterNote.getItemCount() != 0) {
            textViewNote.setVisibility(View.VISIBLE);
        } else {
            textViewNote.setVisibility(View.GONE);

        }

        ItemTouchHelper itemTouchHelperMood = new ItemTouchHelper(simpleItemTouchCallbackMood);
        itemTouchHelperMood.attachToRecyclerView(recyclerViewMood);

        ItemTouchHelper itemTouchHelperNote = new ItemTouchHelper(simpleItemTouchCallbackNote);
        itemTouchHelperNote.attachToRecyclerView(recyclerViewNote);

        ItemTouchHelper itemTouchHelperNoteFood = new ItemTouchHelper(simpleItemTouchCallbackFood);
        itemTouchHelperNoteFood.attachToRecyclerView(recyclerViewFood);


    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallbackMood = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((MoodAdapter) recyclerViewMood.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };

    ItemTouchHelper.SimpleCallback simpleItemTouchCallbackNote = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((NoteAdapter) recyclerViewNote.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };
    ItemTouchHelper.SimpleCallback simpleItemTouchCallbackFood = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((FoodAdapter) recyclerViewFood.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerViews();

    }
}





