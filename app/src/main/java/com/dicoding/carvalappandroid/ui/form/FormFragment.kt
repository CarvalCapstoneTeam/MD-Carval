package com.dicoding.carvalappandroid.ui.form

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.InspectableProperty
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dicoding.carvalappandroid.MainActivity
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.FragmentFormBinding
import com.dicoding.carvalappandroid.ui.otp.OTPActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory
import com.google.android.material.button.MaterialButton

class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null
    private lateinit var customAlertDialog: AlertDialog
    private val viewModel by viewModels<FormViewModel> {
        ViewModelFactory.getInstance(requireActivity(), false)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnSubmit.setOnClickListener {
            val jobName = binding.etName.text.toString()
            val location = binding.etLocation.text.toString()
            val department = binding.etDepartment.text.toString()
            val salary = binding.etSalary.text.toString()
            val profile = binding.etProfile.text.toString()
            val description = binding.etDescription.text.toString()
            val requirement = binding.etRequirement.text.toString()
            val benefit = binding.etBenefits.text.toString()
            var telecommuting = 0

            viewModel.sendResult(jobName, location, department, salary, profile, description, requirement, benefit,telecommuting).observe(viewLifecycleOwner){result->
                when(result){
                    is Result.Loading ->{
                        Log.d("Loading1", "$jobName, $location, $department" )
                        Log.d("Loading2", "$salary, $profile, $requirement" )
                        binding.yesNoRadioGroup.setOnCheckedChangeListener{group, checkedId->
                            when(checkedId){
                                R.id.yesRadioButton -> {
                                    telecommuting = 1
                                }
                                R.id.noRadioButton -> {
                                    telecommuting = 0
                                }
                            }
                        }
                        Log.d("Loading", "$benefit, $telecommuting" )
                    }

                    is Result.Success -> {
                        val builder = AlertDialog.Builder(requireContext())
                        val customAlertDialogView = View.inflate(requireContext(), R.layout.layout_result, null)
                        val logo = customAlertDialogView.findViewById<ImageView>(R.id.data_real)
                        val resultTitle = customAlertDialogView.findViewById<TextView>(R.id.result_text)
                        val accuracy = customAlertDialogView.findViewById<TextView>(R.id.result_accuracy)
                        val tryAgain = customAlertDialogView.findViewById<MaterialButton>(R.id.btn_try)
                        val home = customAlertDialogView.findViewById<TextView>(R.id.btn_home)

                        if (result.data.predictionResult?.prediction == "real"){
                            accuracy.text = "Real Probability = " + result.data.predictionResult.realProbability.toString()
                        }else{
                            logo.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.result_fake))
                            resultTitle.text = "Fake"
                            resultTitle.setTextColor(Color.RED)
                            accuracy.text = "Fake Probability = " + result.data.predictionResult?.fakeProbability.toString()
                            accuracy.setTextColor(Color.RED)
                        }

                        tryAgain.setOnClickListener{
                            customAlertDialog.dismiss()
                            binding.etName.text = null
                            binding.etLocation.text = null
                            binding.etDepartment.text = null
                            binding.etSalary.text = null
                            binding.etProfile.text = null
                            binding.etDescription.text = null
                            binding.etRequirement.text = null
                            binding.etBenefits.text = null
                            binding.yesNoRadioGroup.clearCheck()
                        }

                        home.setOnClickListener {
                            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
                            customAlertDialog.dismiss()
                        }


                        builder.setView(customAlertDialogView)
                        customAlertDialog = builder.create()
                        customAlertDialog.show()

                        Log.d("Loading", "Messgeaeaea : ${result.data.message}" )
                    }

                    is  Result.Error -> {
                        Log.d("ErrorArticle", "Error : ${result.error}")
                    }
                }
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}