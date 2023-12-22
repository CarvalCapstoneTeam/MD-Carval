package com.dicoding.carvalappandroid.ui.form

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getSession().observe(viewLifecycleOwner){result->
            if (!result.isVerified){
                val overlayLayout = layoutInflater.inflate(R.layout.dim_background, null) as FrameLayout
                val builder = AlertDialog.Builder(requireContext())
                val customAlertDialogView = View.inflate(requireContext(), R.layout.layout_verify, null)
                val customVerifyButton = customAlertDialogView.findViewById<MaterialButton>(R.id.btn_verify)
                val customContinueButton = customAlertDialogView.findViewById<MaterialButton>(R.id.btn_continue)
                customVerifyButton.setOnClickListener{
                    val intent = Intent(requireContext(), OTPActivity::class.java)
                    intent.putExtra("token", result.token)
                    intent.putExtra("email", result.email)
                    startActivity(intent)
                }
                customContinueButton.setOnClickListener{
                    customAlertDialog.dismiss()
                    findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
                }
                builder.setView(customAlertDialogView)
                customAlertDialog = builder.create()
                (requireActivity().window.decorView as ViewGroup).addView(overlayLayout)
                customAlertDialog.window?.setDimAmount(0.8f)
                if(!requireActivity().isFinishing){
                    customAlertDialog.show()
                }
                customAlertDialog.setOnDismissListener {
                    (context as? FragmentActivity)?.let { activity ->
                        activity.window.decorView as? ViewGroup
                    }?.removeView(overlayLayout)
                }
            }
        }

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

            if(jobName.isEmpty()){
                binding.etName.error = "This field must not be empty"
            }else if(location.isEmpty()){
                binding.etLocation.error = "This field must not be empty"
            }else if(salary.isEmpty()){
                binding.etSalary.error = "This field must not be empty"
            }else if(profile.isEmpty()){
                binding.etProfile.error = "This field must not be empty"
            }else {
                viewModel.sendResult(
                    jobName,
                    location,
                    department,
                    salary,
                    profile,
                    description,
                    requirement,
                    benefit,
                    telecommuting
                ).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            Log.d("Loading1", "$jobName, $location, $department")
                            Log.d("Loading2", "$salary, $profile, $requirement")
                            binding.yesNoRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                                when (checkedId) {
                                    R.id.yesRadioButton -> {
                                        telecommuting = 1
                                    }

                                    R.id.noRadioButton -> {
                                        telecommuting = 0
                                    }
                                }
                            }
                            Log.d("Loading", "$benefit, $telecommuting")
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            val builder = AlertDialog.Builder(requireContext())
                            val customAlertDialogView =
                                View.inflate(requireContext(), R.layout.layout_result, null)
                            val logo = customAlertDialogView.findViewById<ImageView>(R.id.data_real)
                            val resultTitle =
                                customAlertDialogView.findViewById<TextView>(R.id.result_text)
                            val accuracy =
                                customAlertDialogView.findViewById<TextView>(R.id.result_accuracy)
                            val tryAgain =
                                customAlertDialogView.findViewById<MaterialButton>(R.id.btn_try)
                            val home = customAlertDialogView.findViewById<TextView>(R.id.btn_home)

                            if (result.data.predictionResult?.prediction == "Real") {
                                accuracy.text =
                                    "Real Probability = " + result.data.predictionResult.realProbability.toString()
                                Log.d(
                                    "Log",
                                    "Message : " + result.data.predictionResult.fakeProbability.toString()
                                )
                            } else {
                                logo.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.result_fake
                                    )
                                )
                                resultTitle.text = "Fake"
                                resultTitle.setTextColor(Color.RED)
                                accuracy.text =
                                    "Fake Probability = " + result.data.predictionResult?.fakeProbability.toString()
                                Log.d(
                                    "Log",
                                    "Message : " + result.data.predictionResult?.realProbability.toString()
                                )
                                accuracy.setTextColor(Color.RED)
                            }

                            tryAgain.setOnClickListener {
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

                            Log.d("Loading", "Message : ${result.data.message}")
                        }

                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Timeout/Your account is not verified yet",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("ErrorArticle", "Error : ${result.error}")
                        }
                    }
                }
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return root
    }

    private fun showLoading(it: Boolean?) {
        binding.progressBar.visibility = if (it==true) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}