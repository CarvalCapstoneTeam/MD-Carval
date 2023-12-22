package com.dicoding.carvalappandroid.ui.about

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
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
import com.dicoding.carvalappandroid.R
import com.dicoding.carvalappandroid.databinding.FragmentAboutBinding
import com.dicoding.carvalappandroid.ui.onboarding.BoardingActivity
import com.dicoding.carvalappandroid.ui.tos.TermsOfServiceActivity
import com.dicoding.carvalappandroid.utils.Result
import com.dicoding.carvalappandroid.utils.ViewModelFactory

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private lateinit var customAlertDialog: AlertDialog
    private val viewModel by viewModels<AboutViewModel> {
        ViewModelFactory.getInstance(requireActivity(), false)
    }

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

        viewModel.getSession().observe(viewLifecycleOwner) { session ->
            binding.tvName.text = session.username
            binding.tvEmail.text = session.email
        }

        viewModel.getDataUser().observe(viewLifecycleOwner) { result ->
            when(result){
                is Result.Loading->{
                    //none
                }

                is Result.Success->{
                    viewModel.saveDataUser(
                        result.data.name.toString(),
                        result.data.email.toString()
                    )
                }

                is Result.Error->{
                    Toast.makeText(
                        requireContext(),
                        "Fail to load data",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

        binding.btnEdit.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            val customAlertDialogView =
                View.inflate(requireContext(), R.layout.layout_profile_change, null)
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

            viewModel.getSession().observe(viewLifecycleOwner) { session ->
                etData1.text = session.username
                etData2.text = session.email
            }

            btnSubmit.setOnClickListener {
                if (etData1.text.isEmpty()) {
                    etData1.error = "This Field cannot be empty"
                    etData1.requestFocus()
                } else if (etData2.text.isEmpty()) {
                    etData2.error = "This Field cannot be empty"
                    etData2.requestFocus()
                } else {
                    viewModel.updateProfile(etData1.text.toString(), etData2.text.toString())
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Success -> {
                                    progressBar.visibility = View.GONE
                                    Log.d("Log", "Message : ${result.data.message}")
                                    Toast.makeText(
                                        requireContext(),
                                        "Changes has been made successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    viewModel.saveDataUser(
                                        etData1.text.toString(),
                                        etData2.text.toString()
                                    )
                                    customAlertDialog.dismiss()
                                }

                                is Result.Error -> {
                                    progressBar.visibility = View.GONE
                                    Log.d("Log", "Message : ${result.error}")
                                    Toast.makeText(
                                        requireContext(),
                                        "The email field must be a valid email address",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                                is Result.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                }
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
            val customAlertDialogView =
                View.inflate(requireContext(), R.layout.layout_profile_change, null)
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

            etData2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //none
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //none
                }

                override fun afterTextChanged(p0: Editable?) {
                    val password = p0.toString()

                    if (p0 != null) {
                        if (!password.any { it.isDigit() }) {
                            etData2.error = "Password must contain at least 1 number"
                        } else if (!password.any { it.isUpperCase() }) {
                            etData2.error =
                                "Password must contain at least 1 uppercase letter"
                        }
                    }
                }

            })

            etData3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //none
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //none
                }

                override fun afterTextChanged(p0: Editable?) {
                    val password = p0.toString()

                    if (p0 != null) {
                        if (!password.any { it.isDigit() }) {
                            etData3.error = "Password must contain at least 1 number"
                        } else if (!password.any { it.isUpperCase() }) {
                            etData3.error =
                                "Password must contain at least 1 uppercase letter"
                        }
                    }
                }

            })

            btnSubmit.setOnClickListener {
                if (etData1.text.isEmpty()) {
                    etData1.error = "This Field cannot be empty"
                    etData1.requestFocus()
                } else if (etData2.text.isEmpty()) {
                    etData2.error = "This Field cannot be empty"
                    etData2.requestFocus()
                } else if (etData3.text.isEmpty()) {
                    etData3.error = "This Field cannot be empty"
                    etData3.requestFocus()
                } else {
                    viewModel.changePassword(
                        etData1.text.toString(),
                        etData2.text.toString(),
                        etData3.text.toString()
                    ).observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Result.Success -> {
                                progressBar.visibility = View.GONE
                                Log.d("Log", "Message : ${result.data.message}")
                                Toast.makeText(
                                    requireContext(),
                                    "Changes has been made successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                customAlertDialog.dismiss()
                            }

                            is Result.Error -> {
                                progressBar.visibility = View.GONE
                                Log.d("Log", "Message : ${result.error}")
                                Toast.makeText(requireContext(), "The new password field confirmation does not match", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is Result.Loading -> {
                                progressBar.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
            builder.setView(customAlertDialogView)
            customAlertDialog = builder.create()
            customAlertDialog.show()
        }

        binding.btnTos.setOnClickListener {
            val intentTos = Intent(requireActivity(), TermsOfServiceActivity::class.java)
            startActivity(intentTos)
        }

        binding.btnLogout.setOnClickListener {
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