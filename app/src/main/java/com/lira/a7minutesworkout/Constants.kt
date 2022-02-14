package com.lira.a7minutesworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{

        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
        exerciseList.add(jumpingJacks)

        val highKnees = ExerciseModel(2, "High Knees", R.drawable.ic_high_knees_running_in_place, false, false)
        exerciseList.add(highKnees)

        val pushUp = ExerciseModel(3, "Push up", R.drawable.ic_push_up, false, false)
        exerciseList.add(pushUp)

        val pushUpRotation = ExerciseModel(4, "Push up with Rotation", R.drawable.ic_push_up_and_rotation, false, false)
        exerciseList.add(pushUpRotation)

        val lunge = ExerciseModel(5, "Lunges", R.drawable.ic_lunge, false, false)
        exerciseList.add(lunge)

        val squat = ExerciseModel(6, "Squat", R.drawable.ic_squat, false, false)
        exerciseList.add(squat)

        val stepUpOntoChair = ExerciseModel(7, "Set Up Onto Chair", R.drawable.ic_step_up_onto_chair, false, false)
        exerciseList.add(stepUpOntoChair)

        val wallSit = ExerciseModel(8, "Wall sit", R.drawable.ic_wall_sit, false, false)
        exerciseList.add(wallSit)

        val tricepsDip = ExerciseModel(9, "Triceps Dip On Chair", R.drawable.ic_triceps_dip_on_chair, false, false)
        exerciseList.add(tricepsDip)

        val abdominalCrunch = ExerciseModel(10, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
        exerciseList.add(abdominalCrunch)

        val sidePlank = ExerciseModel(11, "Side Plank", R.drawable.ic_side_plank, false, false)
        exerciseList.add(sidePlank)

        val plank = ExerciseModel(12, "Plank", R.drawable.ic_plank, false, false)
        exerciseList.add(plank)

        return exerciseList
    }

}