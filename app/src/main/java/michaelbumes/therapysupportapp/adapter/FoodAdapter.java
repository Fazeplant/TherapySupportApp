package michaelbumes.therapysupportapp.adapter;

/**
 * Created by Michi on 08.04.2018.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.NoteActivity;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.alarms.AlarmMain;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.entity.MoodDiary;
import michaelbumes.therapysupportapp.fragments.DrugEvent;
import michaelbumes.therapysupportapp.fragments.DrugFragment;

/**
 * Created by Michi on 07.04.2018.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    List<MoodDiary> food;
    private Context context;
    int instanceInt = 0;


    public FoodAdapter(List<MoodDiary> food, Calendar calStartOfDay, Calendar calEndOfDay) {

        List<MoodDiary> returnList = new ArrayList<>();
        for (int i = 0; i < food.size(); i++) {
            if (food.get(i).getArtID() == 2 && food.get(i).getDate().getTime() > calStartOfDay.getTime().getTime() && food.get(i).getDate().getTime() < calEndOfDay.getTime().getTime()) {
                returnList.add(food.get(i));

            }
        }
        this.food = returnList;
    }

    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_cardview_food, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodAdapter.ViewHolder holder, final int position) {
        List<String> arrayList = new ArrayList<String>(Arrays.asList(food.get(position).getInfo1().split(",")));
        holder.textViewFood.setText(arrayList.get(0));
        if (arrayList.size() >1){
            switch (Integer.valueOf(arrayList.get(1))){
                case 2:
                    holder.textViewFoodType.setText("Frühstück");
                    break;
                case 3:
                    holder.textViewFoodType.setText("Mittagessen");
                    break;
                case 6:
                    holder.textViewFoodType.setText("Abendessen");
                    break;
                default:
                    holder.textViewFoodType.setText("Snack");
                    break;
            }
        }
        if (food.get(position).getInfo2() != null) {
            File imgFile = new File(food.get(position).getInfo2());
            if (imgFile.exists()) {
                //Bild korrekt drehen, falls Gerät nicht kompatibel
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(food.get(position).getInfo2());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int rotationInDegrees = NoteActivity.exifToDegrees(rotation);
                Matrix matrix = new Matrix();
                if (rotation != 0f) {
                    matrix.preRotate(rotationInDegrees);
                }
                Bitmap myBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(food.get(position).getInfo2()), 150, 150);

                //Bitmap myBitmap = BitmapFactory.decodeFile(food.get(position).getInfo2());

                Bitmap adjustedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
                holder.imageView.setImageBitmap(adjustedBitmap);


            }
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (food.get(position).getInfo2() != null) {
                        holder.zoomImageFromThumb(holder.imageView);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public void deleteItem(int adapterPosition) {
        AppDatabase.getAppDatabase(context).moodDiaryDao().delete(food.get(adapterPosition));

        food.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, food.size());
        Toast.makeText(context, "Stimmungtagebuch Eintrag wurde gelöscht", Toast.LENGTH_SHORT).show();

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewFood, textViewFoodType;
        public ImageView imageView;
        private Animator mCurrentAnimator;
        private int mShortAnimationDuration;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewFood = itemView.findViewById(R.id.text_view_food_today);
            textViewFoodType = itemView.findViewById(R.id.text_view_food_type_today);
            imageView = itemView.findViewById(R.id.image_view_food_today);

            mShortAnimationDuration = context.getResources().getInteger(
                    android.R.integer.config_shortAnimTime);


        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Intent myIntent = new Intent(context, michaelbumes.therapysupportapp.activities.FoodActivity.class);
            myIntent.putExtra("foodId", food.get(position).getId());
            context.startActivity(myIntent);


        }

        private void zoomImageFromThumb(final View thumbView) {
            // If there's an animation in progress, cancel it
            // immediately and proceed with this one.
            if (mCurrentAnimator != null) {
                mCurrentAnimator.cancel();
            }

            // Load the high-resolution "zoomed-in" image.
            final ImageView expandedImageView = (ImageView) itemView.findViewById(
                    R.id.image_view_food_today_expanded);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(food.get(getAdapterPosition()).getInfo2());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = NoteActivity.exifToDegrees(rotation);
            Matrix matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }

            Bitmap myBitmap = BitmapFactory.decodeFile(food.get(getAdapterPosition()).getInfo2());

            Bitmap adjustedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
            expandedImageView.setImageBitmap(adjustedBitmap);

            // Calculate the starting and ending bounds for the zoomed-in image.
            // This step involves lots of math. Yay, math.
            final Rect startBounds = new Rect();
            final Rect finalBounds = new Rect();
            final Point globalOffset = new Point();

            // The start bounds are the global visible rectangle of the thumbnail,
            // and the final bounds are the global visible rectangle of the container
            // view. Also set the container view's offset as the origin for the
            // bounds, since that's the origin for the positioning animation
            // properties (X, Y).
            thumbView.getGlobalVisibleRect(startBounds);
            itemView.findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
            startBounds.offset(-globalOffset.x, -globalOffset.y);
            finalBounds.offset(-globalOffset.x, -globalOffset.y);

            // Adjust the start bounds to be the same aspect ratio as the final
            // bounds using the "center crop" technique. This prevents undesirable
            // stretching during the animation. Also calculate the start scaling
            // factor (the end scaling factor is always 1.0).
            float startScale;
            if ((float) finalBounds.width() / finalBounds.height()
                    > (float) startBounds.width() / startBounds.height()) {
                // Extend start bounds horizontally
                startScale = (float) startBounds.height() / finalBounds.height();
                float startWidth = startScale * finalBounds.width();
                float deltaWidth = (startWidth - startBounds.width()) / 2;
                startBounds.left -= deltaWidth;
                startBounds.right += deltaWidth;
            } else {
                // Extend start bounds vertically
                startScale = (float) startBounds.width() / finalBounds.width();
                float startHeight = startScale * finalBounds.height();
                float deltaHeight = (startHeight - startBounds.height()) / 2;
                startBounds.top -= deltaHeight;
                startBounds.bottom += deltaHeight;
            }

            // Hide the thumbnail and show the zoomed-in view. When the animation
            // begins, it will position the zoomed-in view in the place of the
            // thumbnail.
            thumbView.setAlpha(0f);
            expandedImageView.setVisibility(View.VISIBLE);


            // Set the pivot point for SCALE_X and SCALE_Y transformations
            // to the top-left corner of the zoomed-in view (the default
            // is the center of the view).
            expandedImageView.setPivotX(0f);
            expandedImageView.setPivotY(0f);

            // Construct and run the parallel animation of the four translation and
            // scale properties (X, Y, SCALE_X, and SCALE_Y).
            AnimatorSet set = new AnimatorSet();
            set
                    .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                            startBounds.left, finalBounds.left))
                    .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                            startBounds.top, finalBounds.top))
                    .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                            startScale, 1f))
                    .with(ObjectAnimator.ofFloat(expandedImageView,
                            View.SCALE_Y, startScale, 1f));
            set.setDuration(mShortAnimationDuration);
            set.setInterpolator(new DecelerateInterpolator());
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCurrentAnimator = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mCurrentAnimator = null;
                }
            });
            set.start();
            mCurrentAnimator = set;

            // Upon clicking the zoomed-in image, it should zoom back down
            // to the original bounds and show the thumbnail instead of
            // the expanded image.
            final float startScaleFinal = startScale;
            expandedImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCurrentAnimator != null) {
                        mCurrentAnimator.cancel();
                    }

                    // Animate the four positioning/sizing properties in parallel,
                    // back to their original values.
                    AnimatorSet set = new AnimatorSet();
                    set.play(ObjectAnimator
                            .ofFloat(expandedImageView, View.X, startBounds.left))
                            .with(ObjectAnimator
                                    .ofFloat(expandedImageView,
                                            View.Y, startBounds.top))
                            .with(ObjectAnimator
                                    .ofFloat(expandedImageView,
                                            View.SCALE_X, startScaleFinal))
                            .with(ObjectAnimator
                                    .ofFloat(expandedImageView,
                                            View.SCALE_Y, startScaleFinal));
                    set.setDuration(mShortAnimationDuration);
                    set.setInterpolator(new DecelerateInterpolator());
                    set.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            thumbView.setAlpha(1f);
                            expandedImageView.setVisibility(View.GONE);
                            mCurrentAnimator = null;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            thumbView.setAlpha(1f);
                            expandedImageView.setVisibility(View.GONE);
                            mCurrentAnimator = null;
                        }
                    });
                    set.start();
                    mCurrentAnimator = set;
                }
            });

        }


    }


}
