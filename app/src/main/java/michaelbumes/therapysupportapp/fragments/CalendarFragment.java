package michaelbumes.therapysupportapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.FoodAdapter;
import michaelbumes.therapysupportapp.adapter.MoodAdapter;
import michaelbumes.therapysupportapp.adapter.NoteAdapter;
import michaelbumes.therapysupportapp.adapter.TakenDrugAdapter;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;
import michaelbumes.therapysupportapp.entity.TakenDrug;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends BaseFragment {

    private Calendar calStartOfDay, calEndOfDay;
    private RecyclerView recyclerViewMood;
    private RecyclerView recyclerViewNote;
    private RecyclerView recyclerViewFood;
    private View mView;
    private CompactCalendarView compactCalendarView;
    private TextView textViewMood, textViewNote, textViewFood, textViewTakenDrug;
    private TextView textViewMonth;


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
        Bundle mBundle = savedInstanceState;
        textViewMonth = view.findViewById(R.id.text_view_month);


        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        String dateMonth = dateFormat.format(Calendar.getInstance().getTime());


        compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        textViewMonth.setText(dateMonth);
        //Bekommt alle moodDiary Einträge und färbt den Kalender mit der Farbe der durchschnittlichen Stimmung ein
        List<MoodDiary> moodDiaryList =AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getAllByArtId(1);
        for (int i = 0; i < moodDiaryList.size(); i++) {
            Date date = moodDiaryList.get(i).getDate();
            Calendar calStartOfDay = Calendar.getInstance(TimeZone.getDefault());
            calStartOfDay.setTime(date);
            calStartOfDay.set(Calendar.HOUR_OF_DAY, 0);
            calStartOfDay.set(Calendar.MINUTE, 0);
            calStartOfDay.set(Calendar.SECOND, 0);
            calStartOfDay.set(Calendar.MILLISECOND, 0);

            Calendar calEndOfDay = Calendar.getInstance(TimeZone.getDefault());
            calEndOfDay.setTime(date);
            calEndOfDay.set(Calendar.HOUR_OF_DAY, 23);
            calEndOfDay.set(Calendar.MINUTE, 59);
            calEndOfDay.set(Calendar.SECOND, 59);
            calEndOfDay.set(Calendar.MILLISECOND, 999);
            List<MoodDiary> moodDiaryListForDayAll = AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getFromTable(calStartOfDay.getTime(), calEndOfDay.getTime());
            List<MoodDiary> moodDiaryListForDayMood = new ArrayList<>();
            for (int j = 0; j < moodDiaryListForDayAll.size(); j++) {
                if (moodDiaryListForDayAll.get(j).getArtID() ==1){
                    moodDiaryListForDayMood.add(moodDiaryListForDayAll.get(j));
                }
            }
            int color = calculateAverageMoodColor(moodDiaryListForDayMood);


            Event event = new Event(color, date.getTime());
            compactCalendarView.addEvent(event);

        }

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);

                calStartOfDay = Calendar.getInstance(TimeZone.getDefault());
                calStartOfDay.setTime(dateClicked);
                calStartOfDay.set(Calendar.HOUR_OF_DAY, 0);
                calStartOfDay.set(Calendar.MINUTE, 0);
                calStartOfDay.set(Calendar.SECOND, 0);
                calStartOfDay.set(Calendar.MILLISECOND, 0);

                calEndOfDay = Calendar.getInstance(TimeZone.getDefault());
                calEndOfDay.setTime(dateClicked);
                calEndOfDay.set(Calendar.HOUR_OF_DAY, 23);
                calEndOfDay.set(Calendar.MINUTE, 59);
                calEndOfDay.set(Calendar.SECOND, 59);
                calEndOfDay.set(Calendar.MILLISECOND, 999);
                updateRecyclerViews();


            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                switch (firstDayOfNewMonth.getMonth()){
                    case 0:
                        textViewMonth.setText(R.string.january);
                        break;
                    case 1:
                        textViewMonth.setText(R.string.february);
                        break;
                    case 2:
                        textViewMonth.setText(R.string.march);
                        break;
                    case 3:
                        textViewMonth.setText(R.string.april);
                        break;
                    case 4:
                        textViewMonth.setText(R.string.may);
                        break;
                    case 5:
                        textViewMonth.setText(R.string.june);
                        break;
                    case 6:
                        textViewMonth.setText(R.string.july);
                        break;
                    case 7:
                        textViewMonth.setText(R.string.august);
                        break;
                    case 8:
                        textViewMonth.setText(R.string.september);
                        break;
                    case 9:
                        textViewMonth.setText(R.string.october);
                        break;
                    case 10:
                        textViewMonth.setText(R.string.november);
                        break;
                    case 11:
                        textViewMonth.setText(R.string.december);
                        break;



                }
            }
        });




        Date date = new Date(Calendar.getInstance().getTime().getTime());


        calStartOfDay = Calendar.getInstance(TimeZone.getDefault());
        calStartOfDay.setTime(date);
        calStartOfDay.set(Calendar.HOUR_OF_DAY, 0);
        calStartOfDay.set(Calendar.MINUTE, 0);
        calStartOfDay.set(Calendar.SECOND, 0);
        calStartOfDay.set(Calendar.MILLISECOND, 0);

        calEndOfDay = Calendar.getInstance(TimeZone.getDefault());
        calEndOfDay.setTime(date);
        calEndOfDay.set(Calendar.HOUR_OF_DAY, 23);
        calEndOfDay.set(Calendar.MINUTE, 59);
        calEndOfDay.set(Calendar.SECOND, 59);
        calEndOfDay.set(Calendar.MILLISECOND, 999);
        textViewMood = view.findViewById(R.id.text_view_mood_calendar);
        textViewNote = view.findViewById(R.id.text_view_note_calendar);
        textViewFood = view.findViewById(R.id.text_view_food_calendar);
        textViewTakenDrug = view.findViewById(R.id.text_view_taken_drug_calendar);

        updateRecyclerViews();







    }

    private int calculateAverageMoodColor(List<MoodDiary> moodDiary) {
        int count = 0;
        int plus = 0;
        int average;
        if (moodDiary.size() >1){
            for (int i = 0; i < moodDiary.size(); i++) {
                plus = plus + Integer.valueOf(moodDiary.get(i).getInfo1());
                count = count + 1;

            }
            average = plus/count;
        }else {
            average = Integer.parseInt(moodDiary.get(0).getInfo1());
        }

        if (average == -3) {
            return ResourcesCompat.getColor(getResources(), R.color.mood_0, null);
        } else if (average <= -2) {
            return ResourcesCompat.getColor(getResources(), R.color.mood_1, null);
        } else if (average <= -1) {
            return ResourcesCompat.getColor(getResources(), R.color.mood_2, null);
        } else if (average == 0 &&count ==0) {
            return ResourcesCompat.getColor(getResources(), R.color.mood_normal, null);
        } else if (average == 0 ) {
            return Color.YELLOW;
        } else if (average < 2) {
            return ResourcesCompat.getColor(getResources(), R.color.mood_3, null);
        } else if (average < 3) {
            return ResourcesCompat.getColor(getResources(), R.color.mood_4, null);
        } else if (average <4) {
            return ResourcesCompat.getColor(getResources(), R.color.mood_5, null);
        }
        return Color.YELLOW;

    }

    //Zeigt die RecyclerViews der Einträge an
    private void updateRecyclerViews() {

        List<MoodDiary> moodDiaries = AppDatabase.getAppDatabase(getContext()).moodDiaryDao().getAll();
        List<TakenDrug> takenDrugs = AppDatabase.getAppDatabase(getContext()).takenDrugDao().getAll();

        recyclerViewMood = mView.findViewById(R.id.mood_recyler_view);

        recyclerViewMood.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapterMood = new MoodAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewMood.setAdapter(adapterMood);

        recyclerViewNote = mView.findViewById(R.id.note_recyler_view);

        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapterNote = new NoteAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewNote.setAdapter(adapterNote);

        recyclerViewFood = mView.findViewById(R.id.food_recyler_view);

        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapterFood = new FoodAdapter(moodDiaries, calStartOfDay, calEndOfDay);
        recyclerViewFood.setAdapter(adapterFood);

        RecyclerView recyclerViewTakenDrug = mView.findViewById(R.id.taken_drug_recyler_view);

        recyclerViewTakenDrug.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapterTakenDrug = new TakenDrugAdapter(takenDrugs, calStartOfDay, calEndOfDay);
        recyclerViewTakenDrug.setAdapter(adapterTakenDrug);

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
        if (adapterTakenDrug.getItemCount() != 0){
            textViewTakenDrug.setVisibility(View.VISIBLE);
        }else {
            textViewTakenDrug.setVisibility(View.GONE);
        }

        ItemTouchHelper itemTouchHelperMood = new ItemTouchHelper(simpleItemTouchCallbackMood);
        itemTouchHelperMood.attachToRecyclerView(recyclerViewMood);

        ItemTouchHelper itemTouchHelperNote = new ItemTouchHelper(simpleItemTouchCallbackNote);
        itemTouchHelperNote.attachToRecyclerView(recyclerViewNote);

        ItemTouchHelper itemTouchHelperNoteFood = new ItemTouchHelper(simpleItemTouchCallbackFood);
        itemTouchHelperNoteFood.attachToRecyclerView(recyclerViewFood);


    }
    //Sind für das Wegwischen zur löschung zuständig
    private final ItemTouchHelper.SimpleCallback simpleItemTouchCallbackMood = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((MoodAdapter) recyclerViewMood.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };

    private final ItemTouchHelper.SimpleCallback simpleItemTouchCallbackNote = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            ((NoteAdapter) recyclerViewNote.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
        }
    };
    private final ItemTouchHelper.SimpleCallback simpleItemTouchCallbackFood = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
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





