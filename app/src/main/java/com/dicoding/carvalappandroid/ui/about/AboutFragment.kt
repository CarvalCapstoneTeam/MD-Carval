package com.dicoding.carvalappandroid.ui.about

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicoding.carvalappandroid.MainActivity
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.FragmentAboutBinding
import com.dicoding.carvalappandroid.databinding.FragmentArticleBinding
import com.dicoding.carvalappandroid.setting.SettingsActivity
import com.dicoding.carvalappandroid.ui.login.LoginActivity
import com.dicoding.carvalappandroid.ui.onboarding.BoardingActivity
import com.dicoding.carvalappandroid.ui.otp.OTPActivity
import com.dicoding.carvalappandroid.ui.tos.TermsOfServiceActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.UserModel
import com.dicoding.carvalappandroid.utils.ViewModelFactory
import com.google.android.material.button.MaterialButton

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private lateinit var customAlertDialog: AlertDialog
    private val viewModel by viewModels<AboutViewModel> {
        ViewModelFactory.getInstance(requireActivity(), false)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        viewModel.getSession().observe(requireActivity()){session->
            binding.tvName.setText(session.username)
            binding.tvEmail.setText(session.email)
        }

        binding.btnEdit.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            val customAlertDialogView = View.inflate(requireContext(), R.layout.layout_profile_change, null)
            val tvData1 = customAlertDialogView.findViewById<TextView>(R.id.tv_data1)
            val etData1 = customAlertDialogView.findViewById<TextView>(R.id.et_data1)
            val tvData2 = customAlertDialogView.findViewById<TextView>(R.id.tv_data2)
            val etData2 = customAlertDialogView.findViewById<TextView>(R.id.et_data2)
            val tvData3 = customAlertDialogView.findViewById<TextView>(R.id.tv_data3)
            val etData3 = customAlertDialogView.findViewById<TextView>(R.id.et_data3)
            val btnSubmit = customAlertDialogView.findViewById<TextView>(R.id.submit)
            val progressBar = customAlertDialogView.findViewById<ProgressBar>(R.id.progressBar)

            tvData3.visibility = View.GONE
            etData3.visibility = View.GONE

            tvData1.text = "Nama"
            tvData2.text = "Email"

            viewModel.getSession().observe(requireActivity()){session->
                etData1.setText(session.username)
                etData2.setText(session.email)
            }
            
            btnSubmit.setOnClickListener {
                viewModel.updateProfile(etData1.text.toString(), etData2.text.toString()).observe(viewLifecycleOwner){result->
                    when(result){
                        is Result.Success->{
                            progressBar.visibility = View.GONE
                            Log.d("Log", "Message : ${result.data.message}")
                            viewModel.saveDataUser(etData1.text.toString(), etData2.text.toString())
                            customAlertDialog.dismiss()
                        }

                        is Result.Error -> {
                            progressBar.visibility = View.GONE
                            Log.d("Log", "Message : ${result.error}")
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                        }

                        is Result.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
            
            builder.setView(customAlertDialogView)
            customAlertDialog = builder.create()
            customAlertDialog.show()
        }

        binding.btnChange.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val customAlertDialogView = View.inflate(requireContext(), R.layout.layout_profile_change, null)
            val tvData1 = customAlertDialogView.findViewById<TextView>(R.id.tv_data1)
            val etData1 = customAlertDialogView.findViewById<TextView>(R.id.et_data1)
            val tvData2 = customAlertDialogView.findViewById<TextView>(R.id.tv_data2)
            val etData2 = customAlertDialogView.findViewById<TextView>(R.id.et_data2)
            val tvData3 = customAlertDialogView.findViewById<TextView>(R.id.tv_data3)
            val etData3 = customAlertDialogView.findViewById<TextView>(R.id.et_data3)
            val btnSubmit = customAlertDialogView.findViewById<TextView>(R.id.submit)
            val progressBar = customAlertDialogView.findViewById<ProgressBar>(R.id.progressBar)

            tvData1.text = "Current Password"
            tvData1.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            tvData2.text = "New Password"
            tvData2.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            tvData3.text = "New Password Confirmation"
            tvData3.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

            btnSubmit.setOnClickListener {
                viewModel.changePassword(etData1.text.toString(), etData2.text.toString(), etData3.text.toString()).observe(viewLifecycleOwner){result->
                    when(result){
                        is Result.Success->{
                            progressBar.visibility = View.GONE
                            Log.d("Log", "Message : ${result.data.message}")
                            customAlertDialog.dismiss()
                        }

                        is Result.Error -> {
                            progressBar.visibility = View.GONE
                            Log.d("Log", "Message : ${result.error}")
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                        }

                        is Result.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
            builder.setView(customAlertDialogView)
            customAlertDialog = builder.create()
            customAlertDialog.show()
        }

        binding.btnTos.setOnClickListener{
            val intentTos = Intent(requireActivity(), TermsOfServiceActivity::class.java)
            startActivity(intentTos)
        }

        binding.btnLogout.setOnClickListener{
            val intent = Intent(requireActivity(), BoardingActivity::class.java)
            startActivity(intent)
            viewModel.logout()
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}